package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.service.OpsinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the response from OPSIN library
 */
@Tag(name = "Structure from Systematic IUPAC",
        description = "API endpoints for getting chemical structure data through Systematic IUPAC name using OPSIN. ")
@SecurityRequirement(name = "api_key") // no need for api_key for this endpoint
@Slf4j
@RestController
public class OpsinResource {

    final private OpsinService opsinService;

    public OpsinResource(OpsinService opsinService) {
        this.opsinService = opsinService;
    }

    @Operation(summary = "Get InChI")
    @RequestMapping(value = "chemical/opsin/to-inchi/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toInChI(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException {

        return opsinService.toInChI(name);
    }

    @Operation(summary = "Get InChIKey")
    @RequestMapping(value = "chemical/opsin/to-inchikey/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toInChIKey(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException {

        return opsinService.toInChIKey(name);
    }

    @Operation(summary = "Get Smiles")
    @RequestMapping(value = "chemical/opsin/to-smiles/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String toSmiles(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException {

        return opsinService.toSmiles(name);
    }
}
