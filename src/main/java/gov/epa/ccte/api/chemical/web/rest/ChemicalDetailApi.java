package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import gov.epa.ccte.api.chemical.projection.chemicaldetail.Compact;
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
        description = "Collection of endpoints with chemical details. This curated data is sourced from the US EPA's Distributed Structure-Searchable Toxicity (DSSTox) database.")
@SecurityRequirement(name = "api_key")
@RequestMapping( value = "chemical")
public interface ChemicalDetailApi {

	/**
	 * {@code GET  /chemical/all} : get pages of all chemicalDetails.
	 *
	 * @param n/a.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 */
    @Operation(summary = "Get all data", description = "return all chemical details. The next parameter allows pagination for retrieval, but retrieval is limited to batches of 1000 per request. Please consider downloading the DSSTOX database instead accessing this information via API.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard2.class, ChemicalDetailAllIds.class}))),
    })
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
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
	@Operation(summary = "Get data by DTXSID",
            description = "return chemical details for given DTXSID. Optionally, user can specify a projection (set of attributes) to return.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class})))
    })
    @GetMapping(value = "/detail/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
	ChemicalDetailBase detailByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
            @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection);

	/**
	 * {@code GET  /chemical/detail/by-smiles/:smiles} : get Compact list of chemicalDetail for the "SMILES".
	 *
	 * @param SMILES the matching SMILES of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of compact chemicalDetail}.
	 */
	@Operation(summary = "Get data by SMILES",
            description = "return chemical details for given SMILES. Available projection is Compact.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {Compact.class})))
    })
    @GetMapping(value = "/detail/search/by-smiles/", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Compact> detailBySmiles(@Parameter(required = true, description = "SMILES String", example = "CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1") 
							@RequestParam(value = "smiles", required = true) String smiles);
 

	/**
	 * {@code GET  /chemical/detail/by-dtxcid/:dtxcid} : get list of chemicalDetail for the "dtxcid".
	 *
	 * @param dtxcid the matching dtxcid of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 * chemicaldetailall, chemicaldetailstandard, chemicalidentifier, chemicalstructure, ntatoolkit
	 */
	@Operation(summary = "Get data by DTXCID",
            description = "return chemical details for given DTXCID. Optionally, user can specify a projection (set of attributes) to return.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class, ChemicalDetailAll.class, NtaToolkit.class})))
    })
    @GetMapping(value = "/detail/search/by-dtxcid/{dtxcid}", produces = MediaType.APPLICATION_JSON_VALUE)
	ChemicalDetailBase detailsByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505")  @PathVariable("dtxcid") String dtxcid,
            @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection);

	/**
	 * {@code POST  chemical/detail/search/by-dtxsid/ : get list of chemicalDetail for the multiple "dtxsid".
	 * @param BatchRequest the matching dtxsid of the chemicalDetail to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
	 */
	@Operation(summary = "Get data for a batch of DTXSIDs",
            description = "return chemical details for given DTXSIDs. Optionally, user can specify a projection (set of attributes) to return. Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class}))),
            @ApiResponse(responseCode = "400", description = "User submitted more than allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/problem+json",
                            examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports requests of '200' DTXSIDs at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                            schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @PostMapping(value = "/detail/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
	List<?> batchDtxsidSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifiers",
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
	@Operation(summary = "Get data for a batch of DTXCIDs",
            description = "return chemical details for given DTXCIDs. Optionally, user can specify a projection (set of attributes) to return. Note: Maximum ${application.batch-size} DTXCIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class}))),
            @ApiResponse(responseCode = "400", description = "User has submitted more than allowed number (${application.batch-size}) of DTXCID(s).",
                    content = @Content( mediaType = "application/problem+json",
                            examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports requests of '${application.batch-size}' DTXSIDs at one time, '${application.batch-size+1}' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                            schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @PostMapping(value = "/detail/search/by-dtxcid/", produces = MediaType.APPLICATION_JSON_VALUE)
	List<?> batchDtxcidSearch( @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Compound Identifiers",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject("\"[\\\"DTXCID505\\\",\\\"DTXSID9020112\\\"]\"")})})
                    @RequestBody String[] dtxcids,
                    @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall")
                    ChemicalDetailProjection projection);

}

