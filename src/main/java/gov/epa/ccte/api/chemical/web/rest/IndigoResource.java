package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.service.IndigoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

/**
 * REST controller for getting the response from Indigo Toolkit.
 */
@Slf4j
@RestController
public class IndigoResource implements IndigoApi {

    private final IndigoService indigoService;

    public IndigoResource(IndigoService indigoService) {
        this.indigoService = indigoService;
    }

    @Override
    public String toInChI(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2inchi(mol);
    }

    @Override
    public String toInChIkey(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2inchikey(mol);
    }

    @Override
    public String toSmiles(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2smiles(mol);
    }

    @Override
    public String toCanonicalSmiles(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2name(mol);
    }

    @Override
    public Double toMolecularWeight(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2molWeight(mol);
    }

    @Override
    public String toMol2000(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2molv2000(mol);
    }

    @Override
    public String toMol3000(String mol) throws IOException {
        log.debug("mol file size = {}", mol.length());
        return indigoService.mol2molv3000(mol);
    }
}