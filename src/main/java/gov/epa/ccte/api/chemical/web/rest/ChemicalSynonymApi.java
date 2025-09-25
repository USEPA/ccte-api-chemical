package gov.epa.ccte.api.chemical.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import gov.epa.ccte.api.chemical.projection.CcdSynonymFlatProjection;
import gov.epa.ccte.api.chemical.projection.ChemicalSynonymAll;

import java.util.List;

/**
 * API interface for retrieving chemical synonym data.
 */
@Tag(name = "Chemical Synonym Resource",
        description = "API endpoints for getting chemical synonym for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/synonym", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ChemicalSynonymApi {

	@Operation(summary = "Get synonyms by dtxsid with projections",
	        description = "Fetches synonyms based on the specified projection. Available projections: ccd-synonyms (flat list), chemical-synonym-all (default structured view).")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ChemicalSynonymAll.class, CcdSynonymFlatProjection.class})))
	})
	@GetMapping(value = "/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	Object getSynonymsByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182")
	                          @PathVariable("dtxsid") String dtxsid,
	                          @Parameter(description = "Projection type: ccd-synonyms or default")
	                          @RequestParam(value = "projection", required = false) String projection);

    @Operation(summary = "Get synonyms by the batch of dtxsids")
    @PostMapping(value = "/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalSynonymAll.class})))
    })
    List<ChemicalSynonymAll> synoymsByBatchDtxsid(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                                            @RequestBody String[] dtxsids);
}