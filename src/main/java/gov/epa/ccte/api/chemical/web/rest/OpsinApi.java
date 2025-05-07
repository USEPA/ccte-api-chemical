package gov.epa.ccte.api.chemical.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

/**
 * API interface for getting chemical structure data through Systematic IUPAC name using OPSIN.
 */
@Tag(name = "OPSIN Resource",
        description = "API endpoints for getting chemical structure data through Systematic IUPAC name using OPSIN. ")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/opsin")
public interface OpsinApi {

	/**
	 * {@code GET  /to-inchi/{name}} : get InChI for given Systematic IUPAC Name.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body InchI}.
	 */
    @Operation(summary = "Get InChI", description = "return InChI for given Systematic IUPAC Name.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "InChI", value = "InChI=1/C2H5NO/c1-2(3)4/h1H3,(H2,3,4)/f/h3H2")})),
    })
    @GetMapping(value = "/to-inchi/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChI(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException;

	/**
	 * {@code GET  /to-inchikey/{name}} : get InChIKey for given Systematic IUPAC Name.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body InChIKey}.
	 */
    @Operation(summary = "Get InChIKey", description = "return InChIKey for given Systematic IUPAC Name.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "InChIKey", value =  "DLFVBJFMPXGRIB-UHFFFAOYSA-N")})),
    })
    @GetMapping(value = "/to-inchikey/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChIKey(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException;

	/**
	 * {@code GET  /to-smiles/{name}} : get Smiles for given Systematic IUPAC Name.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body Smiles}.
	 */
    @Operation(summary = "Get Smiles", description = "return Smiles for given Systematic IUPAC Name.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(name = "Smiles", value = "C(C)(=O)N")})),
    })
    @GetMapping(value = "/to-smiles/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String toSmiles(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException;
}
