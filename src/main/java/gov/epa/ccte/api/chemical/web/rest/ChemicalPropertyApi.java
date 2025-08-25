package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyAll;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertySummary;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Chemical Property Resource", description = "API endpoints for getting chemical properties (experimental and/or predictive) for a given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
public interface ChemicalPropertyApi {

	 // *********************** Experimental - start *************************************
	
	/**
	 * {@code GET  chemical/property/experimental/search/by-dtxsid/{dtxsid} : get list of experimental properties (Physchem) for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the experimental properties (Physchem) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of experimental properties (Physchem)}.
	 */
	@Operation(summary = "Get experimental properties by dtxsid (Physchem)",
            description = "Specify the dtxsid as part of the path.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyAll.class})))
    })
    @GetMapping(value = "chemical/property/experimental/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> experimentalPropertyByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
	/**
	 * {@code GET  chemical/property/experimental/search/by-range/{propertyName}/{start}/{end} : get list of experimental properties (Physchem) for the "propertyName" and range("start","end").
	 *
	 * @param propertyName and range(start,end) the matching propertyName of the experimental properties (Physchem) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of experimental properties (Physchem)}.
	 */
	@Operation(summary = "Get experimental properties by propertyName and range(start,end) (Physchem)",
            description = "Specify the propertyName, start, and end as part of the path.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyAll.class})))
    })
    @GetMapping(value = "chemical/property/experimental/search/by-range/{propertyName}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> experimentalPropertyByRange(@PathVariable("propertyName") String propertyName, @PathVariable("start") Double start, @PathVariable("end") Double end);

	/**
	 * {@code GET  chemical/property/experimental/name : get list of all experimental property names (Physchem).
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of all experimental property names (Physchem)}.
	 */
	@Operation(summary = "Get all experimental property names (Physchem)")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyNames.class})))
    })
    @GetMapping(value = "chemical/property/experimental/name", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyNames> experimentalPropertyNames();

	/**
	 * {@code POST  chemical/property/experimental/search/by-dtxsid/ : get list of experimental properties (Physchem) for the batch of "dtxsids".
	 *
	 * @param BatchRequest the matching dtxsid of the experimental properties (Physchem) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of experimental properties (Physchem)}.
	 */
    @Operation(summary = "Get experimental properties by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Successfull",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyAll.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/experimental/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> experimentalBatchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})}) @RequestBody String[] dtxsids) throws HigherNumberOfIdsException;
    
    // *********************** Experimental - End *************************************
    // *********************** Predicted - start *************************************
	
	/**
	 * {@code GET  chemical/property/predicted/search/by-dtxsid/{dtxsid} : get list of predicted properties for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the predicted properties to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of predicted properties}.
	 */
	@Operation(summary = "Get predicted properties by dtxsid",
           description = "Specify the dtxsid as part of the path.")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertyPredicted.class})))
   })
    @GetMapping(value = "chemical/property/predicted/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedPropertyByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);

	/**
	 * {@code GET  chemical/property/predicted/search/by-range/{propertyName}/{start}/{end} : get list of predicted properties for the "propertyName" and range("start","end").
	 *
	 * @param propertyName and range(start,end) the matching propertyName of the predicted properties to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of predicted properties}.
	 */
	@Operation(summary = "Get predicted properties by propertyName and range(start,end)",
            description = "Specify the propertyName, start, and end as part of the path.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyPredicted.class})))
    })
    @GetMapping(value = "chemical/property/predicted/search/by-range/{propertyId}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedPropertyByRange(@PathVariable("propertyId") String propertyName, @PathVariable("start") Double start, @PathVariable("end") Double end);

	/**
	 * {@code GET  chemical/property/predicted/name : get list of all predicted property names.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of all predicted property names}.
	 */
	@Operation(summary = "Get all predicted property names")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyNames.class})))
    })
    @GetMapping(value = "chemical/property/predicted/name", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyNames> predictedPropertyNames();

	/**
	 * {@code POST  chemical/property/predicted/search/by-dtxsid/ : get list of predicted properties for the batch of "dtxsids".
	 *
	 * @param BatchRequest the matching dtxsid of the predicted propertiesto retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of predicted properties}.
	 */
    @Operation(summary = "Get predicted properties by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Successfull",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyPredicted.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/predicted/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedBatchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})}) @RequestBody String[] dtxsids) throws HigherNumberOfIdsException;
    
    // *********************** Predicted - End *************************************
    // *********************** Property Summary - start *************************************
    
	/**
	 * {@code GET  chemical/property/summary/search/by-dtxsid/{dtxsid} : get list of property summaries for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the property summaries to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of property summaries}.
	 */
	@Operation(summary = "Get property summary by dtxsid (Physchem)",
           description = "Specify the dtxsid as part of the path.")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummary.class})))
   })
    @GetMapping(value = "chemical/property/summary/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummary> propertySummaryByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
	/**
	 * {@code GET  chemical/property/summary/search/ : get list of property summaries for the "dtxsid" and "propertyName".
	 *
	 * @param dtxsid the matching dtxsid of the property summaries to retrieve.
	 * @param propertyName the matching propertyName of the property summaries to retrieve.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of property summaries}.
	 */
	@Operation(summary = "Get summary by dtxsid and property name (Physchem)",
           description = "Specify the dtxsid and propertyName as parameters.")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummary.class})))
   })
    @GetMapping(value = "chemical/property/summary/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummary> propertySummaryByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);
    
    // *********************** Property Summary - end *************************************
    // *********************** Fate - Start *************************************
    
	/**
	 * {@code GET  chemical/fate/search/by-dtxsid/{dtxsid} : get list of chemical fate (Env. Fate/transport) for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the chemical fate (Env. Fate/transport) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical fates (Env. Fate/transport)}.
	 */
	@Operation(summary = "Get chemical fate by dtxsid (Env. Fate/transport)",
            description = "Specify the dtxsid as part of the path.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyAll.class})))
    })
    @GetMapping(value = "chemical/fate/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> fateByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
	/**
	 * {@code POST  chemical/fate/search/by-dtxsid/ : get list of chemical fates for the batch of "dtxsids".
	 *
	 * @param BatchRequest the matching dtxsid of the chemical fates to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical fates}.
	 */
    @Operation(summary = "Get fate by the batch of dtxsid(s) (Env. Fate/transport)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Successfull",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyAll.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    }) 
    @PostMapping(value = "chemical/fate/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> fateBatchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})}) @RequestBody String[] dtxsids) throws HigherNumberOfIdsException;
    
    // *********************** Fate - End *************************************
    // *********************** Fate Summary - start *************************************
    
	/**
	 * {@code GET  chemical/fate/summary/search/by-dtxsid/{dtxsid} : get list of fate summaries for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the fate summaries to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fate summaries}.
	 */
	@Operation(summary = "Get fate summary by dtxsid (Env. Fate/transport)",
           description = "Specify the dtxsid as part of the path.")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummary.class})))
   })
    @GetMapping(value = "chemical/fate/summary/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummary> fateSummaryByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
	/**
	 * {@code GET  chemical/fate/summary/search/ : get list of fate summaries for the "dtxsid" and "propertyName".
	 *
	 * @param dtxsid the matching dtxsid of the fate summaries to retrieve.
	 * @param propertyName the matching propertyName of the fate summaries to retrieve.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fate summaries}.
	 */
	@Operation(summary = "Get fate summary by dtxsid and property name (Env. Fate/transport)",
           description = "Specify the dtxsid and propertyName as parameters.")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummary.class})))
   })
    @GetMapping(value = "chemical/fate/summary/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummary> fateSummaryByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);

}
