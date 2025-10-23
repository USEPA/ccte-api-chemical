package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for checking availability of data in Pubchem for GHS Safety data
 */
@Slf4j
@RestController
public class WikipediaLinkResource implements WikipediaLinkApi {

    private final ChemicalListChemicalRepository chemicalListChemicalRepository;

    public WikipediaLinkResource(ChemicalListChemicalRepository chemicalListChemicalRepository) {
        this.chemicalListChemicalRepository = chemicalListChemicalRepository;
    }

    @Override
    public WikipediaLinkResponse byDtxsid(String dtxsid) {
        log.debug("Received request to check existence by dtxsid: {} ", dtxsid);

        if (chemicalListChemicalRepository.isWikipediaLinkExists(new String[]{dtxsid}).isEmpty()) {
            return new WikipediaLinkResponse(dtxsid, null);
        } else {
            return chemicalListChemicalRepository.isWikipediaLinkExists(new String[]{dtxsid}).get(0);
        }
    }

    @Override
    public List<WikipediaLinkResponse> byBatchDtxsid(String[] dtxsids) {
        log.debug("Received request to check existence by {} dtxsid: ", dtxsids.length);
        return chemicalListChemicalRepository.isWikipediaLinkExists(dtxsids);
    }
}

