package gov.epa.ccte.api.chemical.service;

import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoException;
import com.epam.indigo.IndigoObject;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.Compact;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimilarSearchService {

    private final ChemicalDetailRepository detailRepository;

    public SimilarSearchService(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }
    
    // Caches for canonical SMILES and IndigoObjects
    private volatile Map<String, String> canonicalToOriginal = new ConcurrentHashMap<>();
    private volatile Map<String, IndigoObject> canonicalToIndigo = new ConcurrentHashMap<>();
    private Indigo indigo;

    private void populateCache() {
        Map<String, String> newCanonicalToOriginal = new ConcurrentHashMap<>();
        Map<String, IndigoObject> newCanonicalToIndigo = new ConcurrentHashMap<>();
        String[] smilesList = detailRepository.getAllSmiles();
        // Use ThreadLocal Indigo for thread safety in parallel
        ThreadLocal<Indigo> threadLocalIndigo = ThreadLocal.withInitial(Indigo::new);
        java.util.Arrays.stream(smilesList)
            .parallel()
            .filter(smiles -> smiles != null)
            .forEach(smiles -> {
                try {
                    Indigo indigoInstance = threadLocalIndigo.get();
                    IndigoObject obj = indigoInstance.loadMolecule(smiles.trim());
                    String canonical = obj.canonicalSmiles();
                    newCanonicalToOriginal.put(canonical, smiles);
                    newCanonicalToIndigo.put(canonical, obj);
                } catch (IndigoException e) {}
                
            });
        canonicalToOriginal = newCanonicalToOriginal;
        canonicalToIndigo = newCanonicalToIndigo;
        log.info("SMILES cache refreshed with " + canonicalToOriginal.size() + " entries.");
    }

    @PostConstruct
    public void initializeCache() {
        indigo = new Indigo();
        populateCache();
    }

    public synchronized void refreshCache() {
        populateCache();
    }

    public List<Compact> similarSearch(String smiles){
        String decodedSmiles;
        try {
            decodedSmiles = java.net.URLDecoder.decode(smiles, java.nio.charset.StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            log.error("Error decoding SMILES: " + e.getMessage());
            return null;
        }
        String canonicalQuerySmiles;
        IndigoObject queryObj;
        try {
            queryObj = indigo.loadMolecule(decodedSmiles);
            canonicalQuerySmiles = queryObj.canonicalSmiles();
        } catch (IndigoException e) {
            log.error("Error canonicalizing query SMILES: " + e.getMessage());
            return null;
        }
        // Fast exact match lookup
        if (canonicalToOriginal.containsKey(canonicalQuerySmiles)) {
            String original = canonicalToOriginal.get(canonicalQuerySmiles);
            log.info("Exact canonical match found for query molecule: " + original);
            return detailRepository.findBySmiles(original);
        }
        // Parallel similarity search using cached IndigoObjects
        class SimilarityResult {
            String canonical;
            float similarity;
            SimilarityResult(String canonical, float similarity) {
                this.canonical = canonical;
                this.similarity = similarity;
            }
        }
        SimilarityResult bestMatch = canonicalToIndigo.entrySet().parallelStream()
            .map(entry -> {
                try {
                    float similarity = indigo.similarity(queryObj, entry.getValue(), "tanimoto");
                    return new SimilarityResult(entry.getKey(), similarity);
                } catch (IndigoException e) {
                    return null;
                }
            })
            .filter(r -> r != null)
            .reduce((r1, r2) -> r1.similarity >= r2.similarity ? r1 : r2)
            .orElse(null);
        if (bestMatch != null) {
            String original = canonicalToOriginal.get(bestMatch.canonical);
            log.info("Returning best match molecule: " + original + " with similarity: " + bestMatch.similarity);
            return detailRepository.findBySmiles(original);
        }
        return null;
    }
    
}