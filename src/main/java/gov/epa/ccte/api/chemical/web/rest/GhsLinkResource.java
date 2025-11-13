package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * REST controller for checking availability of GHS Safety data in PubChem.
 */
@Slf4j
@RestController
public class GhsLinkResource implements GhsLinkApi {

    private final ChemicalListChemicalRepository chemicalListChemicalRepository;

    public GhsLinkResource(ChemicalListChemicalRepository chemicalListChemicalRepository) {
        this.chemicalListChemicalRepository = chemicalListChemicalRepository;
    }

    @Override
    public GhsLinkResponse byDtxsid(String dtxsid) {
        log.debug("Received request to check existence by dtxsid: {} ", dtxsid);

        if (chemicalListChemicalRepository.isGhsLinkExists(new String[]{dtxsid}).isEmpty()) {
            return new GhsLinkResponse(dtxsid, false, null);
        } else {
            return chemicalListChemicalRepository.isGhsLinkExists(new String[]{dtxsid}).get(0);
        }
    }

    @Override
    public List<GhsLinkResponse> byBatchDtxsid(String[] dtxsids) {
        log.debug("Received request to check existence by {} dtxsid: ", dtxsids.length);
        return chemicalListChemicalRepository.isGhsLinkExists(dtxsids);
    }
}