package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.service.IndigoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * REST controller for getting the response from OPSIN library
 */
@Tag(name = "Indigo Service",
        description = "API endpoints for getting chemical info from indigo toolkit. ")
@SecurityRequirement(name = "api_key") // no need for api_key for this endpoint
@Slf4j
@RestController
public class IndigoResource {

    final private IndigoService indigoService;

    public IndigoResource(IndigoService indigoService) {
        this.indigoService = indigoService;
    }

    @Operation(summary = "Get InChI from mol")
    @RequestMapping(value = "chemical/indigo/to-inchi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toInChI(@Parameter(required = true, description = "mol file") @RequestBody() String mol) throws IOException {

        log.debug("mol file size = {}", mol.length());


        return indigoService.mol2inchikey(mol);
    }

    @Operation(summary = "Get Smiles from mol")
    @RequestMapping(value = "chemical/indigo/to-smiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toSmiles(@Parameter(required = true, description = "mol file") @RequestBody() String mol) throws IOException {

        log.debug("mol file size = {}", mol.length());


        return indigoService.mol2smiles(mol);
    }

    @Operation(summary = "Get Canonical Smiles from mol")
    @RequestMapping(value = "chemical/indigo/to-canonicalsmiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toCanonicalSmiles(@Parameter(required = true, description = "mol file") @RequestBody() String mol) throws IOException {

        log.debug("mol file size = {}", mol.length());


        //return indigoService.mol2CanonicalSmiles(mol);
        return indigoService.mol2name(mol);
    }

    @Operation(summary = "Get molecular weight from mol")
    @RequestMapping(value = "chemical/indigo/to-molweight", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Double toMolecularWeight(@Parameter(required = true, description = "mol file") @RequestBody() String mol) throws IOException {

        log.debug("mol file size = {}", mol.length());


        //return indigoService.mol2CanonicalSmiles(mol);
        return indigoService.mol2molWeight(mol);
    }

    @Operation(summary = "Get mol file v2000 from mol")
    @RequestMapping(value = "chemical/indigo/to-mol2000", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toMol2000(@Parameter(required = true, description = "mol file") @RequestBody() String mol) throws IOException {

        log.debug("mol file size = {}", mol.length());


        //return indigoService.mol2CanonicalSmiles(mol);
        return indigoService.mol2molv2000(mol);
    }

    @Operation(summary = "Get mol file v3000 from mol")
    @RequestMapping(value = "chemical/indigo/to-mol3000", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toMol3000(@Parameter(required = true, description = "mol file") @RequestBody() String mol) throws IOException {

        log.debug("mol file size = {}", mol.length());


        //return indigoService.mol2CanonicalSmiles(mol);
        return indigoService.mol2molv3000(mol);
    }

}
