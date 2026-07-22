package gov.epa.ccte.api.chemical.service;

import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoException;
import com.epam.indigo.IndigoInchi;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.Compact;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.UnparseableSmilesException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimilarSearchService {

    private final ChemicalDetailRepository detailRepository;

    public SimilarSearchService(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public List<Compact> searchBySmiles(String smiles) {

        try {
            var indigo = new Indigo();
            var indigoInchi = new IndigoInchi(indigo);

            // Load the molecule
            var molecule = indigo.loadMolecule(smiles);

            // Generate the InChIKey
            var inchiKey = indigoInchi.getInchiKey(indigoInchi.getInchi(molecule));

            return detailRepository.findByInchikey(inchiKey);
        } catch (IndigoException e) {
            throw new UnparseableSmilesException(smiles, e);
        }

    }

}
