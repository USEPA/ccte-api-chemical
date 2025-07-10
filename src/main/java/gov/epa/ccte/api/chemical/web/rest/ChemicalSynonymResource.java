package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.ChemicalSynonymAll;
import gov.epa.ccte.api.chemical.repository.ChemicalSynonymRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

/**
 * REST controller for retrieving chemical synonym data.
 */
@Slf4j
@RestController
public class ChemicalSynonymResource implements ChemicalSynonymApi {

    @Value("${application.batch-size}")
    private Integer batchSize;

    private final ChemicalSynonymRepository repository;

    public ChemicalSynonymResource(ChemicalSynonymRepository synonymRepository) {
        this.repository = synonymRepository;
    }

    @Override
    public Object getSynonymsByDtxsid(String dtxsid, String projection) {
        log.info("dtxsid = {}, projection = {}", dtxsid, projection);

        if ("ccd-synonyms".equalsIgnoreCase(projection)) {
            return repository.getFlatSynonymsByDtxsid(dtxsid);
        }

        return repository.findByDtxsidAndIsPublic(dtxsid, true, ChemicalSynonymAll.class)
                .orElseThrow(() -> new IdentifierNotFoundException("DTXSID", dtxsid));
    }

    @Override
    public List synoymsByBatchDtxsid(String[] dtxsids) {
        log.info("dtxsid size = {}", dtxsids.length);

        if (dtxsids.length > batchSize) {
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        }

        return repository.findByDtxsidInAndIsPublicOrderByDtxsidAsc(Arrays.asList(dtxsids), true, ChemicalSynonymAll.class);
    }
}