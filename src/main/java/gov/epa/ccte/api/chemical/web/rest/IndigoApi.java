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
 * API interface for getting chemical info from Indigo Toolkit.
 */
@Tag(name = "Indigo Service",
        description = "API endpoints for getting chemical info from Indigo Toolkit.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/indigo")
public interface IndigoApi {

	/**
	 * {@code POST  /to-inchi} : get InChI for given mol filee.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body InchI}.
	 */
    @Operation(summary = "Get InChI from mol", description = "return InChI for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "InChI", value = "InChI=1/C2H5NO/c1-2(3)4/h1H3,(H2,3,4)/f/h3H2")})),
    })
    @PostMapping(value = "/to-inchi", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChI(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

	/**
	 * {@code POST  /to-inchikey} : get InChIKey for given mol file.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body InChIKey}.
	 */
    @Operation(summary = "Get InChIKey from mol", description = "return InChIKey for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "InChIKey", value = "DLFVBJFMPXGRIB-UHFFFAOYSA-N")})),
    })
    @PostMapping(value = "/to-inchikey", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChIkey(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

	/**
	 * {@code POST  /to-smiles} : get smiles for given mol file.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body smiles}.
	 */
    @Operation(summary = "Get Smiles from mol", description = "return Smiles for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "Smiles", value = "C(C)(C)(C1C=CC(O)=CC=1)C1C=CC(O)=CC=1")})),
    })
    @PostMapping(value = "/to-smiles", produces = MediaType.APPLICATION_JSON_VALUE)
    String toSmiles(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

	/**
	 * {@code POST  /to-canonicalsmiles} : get canonical smiles for given mol file.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body canonical smiles}.
	 */
    @Operation(summary = "Get Canonical Smiles from mol", description = "return Canonical Smiles for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "Canonical Smiles")})),
    })
    @PostMapping(value = "/to-canonicalsmiles", produces = MediaType.APPLICATION_JSON_VALUE)
    String toCanonicalSmiles(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

	/**
	 * {@code POST  /to-molweight} : get molecular weight for given mol file.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body molecular weight}.
	 */
    @Operation(summary = "Get molecular weight from mol", description = "return molecular weight for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json"))
    })
    @PostMapping(value = "/to-molweight", produces = MediaType.APPLICATION_JSON_VALUE)
    Double toMolecularWeight(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

	/**
	 * {@code POST  /to-mol2000} : get mol file v2000 for given mol file.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body mol file v2000}.
	 */
    @Operation(summary = "Get mol file v2000 from mol", description = "return mol file v2000 for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "mol file v2000")})),
    })
    @PostMapping(value = "/to-mol2000", produces = MediaType.APPLICATION_JSON_VALUE)
    String toMol2000(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

	/**
	 * {@code POST  /to-mol3000} : get mol file v3000 for given mol file.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body mol file v3000}.
	 */
    @Operation(summary = "Get mol file v3000 from mol", description = "return mol file v3000 for given mol file.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
            		examples = {@ExampleObject(name = "mol file v3000")})),
    })
    @PostMapping(value = "/to-mol3000", produces = MediaType.APPLICATION_JSON_VALUE)
    String toMol3000(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;
}
