package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailAll;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailAllIds;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailBase;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailProjection;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailStandard;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailStandard2;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalIdentifier;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalStructure;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.NtaToolkit;
import gov.epa.ccte.api.chemical.web.rest.requests.Page;
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

/**
 * REST controller for getting the {@link ChemicalDetail}s.
 */
@Tag(name = "Chemical Details Resource",
        description = "API endpoints for collecting data for given chemical(s).")
@SecurityRequirement(name = "api_key")
@RequestMapping( value = "chemical")
public interface ChemicalDetailApi {

	/**
	 * {@code GET  /chemical/all} : get pages of all chemicalDetails.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 */
    @Operation(summary = "Find all chemical details", description = "return all chemical details.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard2.class, ChemicalDetailAllIds.class}))),
    })
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	Page getAllChemicalDetails(@RequestParam(value = "next", required = false, defaultValue = "1")Long next,
     				        @Parameter(description = "Specifies if projection is used. Option: all-ids. " +
     				        "If omitted, the default ChemicalDetailStandard2 data is returned.")
     					@RequestParam(value = "projection", required = false) String projection);

	/**
	 * {@code GET  /chemical/detail/by-dtxsid/:dtxsid} : get list of chemicalDetail for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 */
	@Operation(summary = "Get data by dtxsid",
            description = "Specify the dtxsid as part of the path, and optionally user can also define projection (set of attributes to return).")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class})))
    })
    @RequestMapping(value = "/detail/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ChemicalDetailBase detailByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
            @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection);

	/**
	 * {@code GET  /chemical/detail/by-dtxcid/:dtxcid} : get list of chemicalDetail for the "dtxcid".
	 *
	 * @param dtxcid the matching dtxcid of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 * chemicaldetailall, chemicaldetailstandard, chemicalidentifier, chemicalstructure, ntatoolkit
	 */
	@Operation(summary = "Get data by dtxcid",
            description = "Specify the dtxcid as part of the path, and optionally user can also define projection (set of attributes to return).")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class, ChemicalDetailAll.class, NtaToolkit.class})))
    })
    @RequestMapping(value = "/detail/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ChemicalDetailBase detailsByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505")  @PathVariable("dtxcid") String dtxcid,
            @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection);

	/**
	 * {@code POST  chemical/detail/search/by-dtxsid/ : get list of chemicalDetail for the multiple "dtxsid".
	 * @param BatchRequest the matching dtxsid of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 */
	@Operation(summary = "Get data by the batch of dtxsids",
            description = "Besides batch of the values, the user can also define projection (set of attributes to return). Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/problem+json",
                            examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                            schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @RequestMapping(value = "/detail/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	List batchDtxsidSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                    @RequestBody String[] dtxsids,
                    @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall")
                    ChemicalDetailProjection projection);

	/**
	 * {@code POST  chemical/detail/search/by-dtxcid/ : get list of chemicalDetail for the multiple "dtxcid".
	 * @param BatchRequest the matching dtxcid of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 */
	@Operation(summary = "Get data by the batch of dtxcids",
            description = "Besides batch of the values, the user can also define projection (set of attributes to return). Note: Maximum ${application.batch-size} DTXCIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXCID(s).",
                    content = @Content( mediaType = "application/problem+json",
                            examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '${application.batch-size}' dtxsid at one time, '${application.batch-size+1}' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                            schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @RequestMapping(value = "/detail/search/by-dtxcid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	List batchDtxcidSearch( @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Compound Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject("\"[\\\"DTXCID505\\\",\\\"DTXSID9020112\\\"]\"")})})
                    @RequestBody String[] dtxcids,
                    @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall")
                    ChemicalDetailProjection projection);

}
