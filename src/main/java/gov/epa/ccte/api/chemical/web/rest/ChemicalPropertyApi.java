package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.dto.ChemicalFateAllDto;
import gov.epa.ccte.api.chemical.dto.ChemicalFateBatchDto;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.*;
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

@Tag(name = "Chemical Property Resource", 
description = "Collection of endpoints for experimental and predictive chemical properties. This curated data is sourced from the US EPA's Distributed Structure-Searchable Toxicity (DSSTox) database and the Toxicity Estimation Software Tool (TEST) suite of QSAR models.")
@SecurityRequirement(name = "api_key")
public interface ChemicalPropertyApi {

	 // *********************** Experimental - start *************************************
	
	/**
	 * {@code GET  chemical/property/experimental/search/by-dtxsid/{dtxsid} : get list of experimental properties (Physchem) for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the experimental properties (Physchem) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of experimental properties (Physchem)}.
	 */
	@Operation(summary = "Get experimental properties by DTXSID",
            description = "return experimental properties for given DTXSID")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyExperimental.class})))
    })
    @GetMapping(value = "chemical/property/experimental/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyExperimental> experimentalPropertyByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
	/**
	 * {@code GET  chemical/property/experimental/search/by-range/{propertyName}/{start}/{end} : get list of experimental properties (Physchem) for the "propertyName" and range("start","end").
	 *
	 * @param propertyName and range(start,end) the matching propertyName of the experimental properties (Physchem) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of experimental properties (Physchem)}.
	 */
	@Operation(summary = "Get experimental properties by property and range",
            description = "return experimental properties by specifying the propertyName, start, and end values")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyExperimental.class})))
    })
    @GetMapping(value = "chemical/property/experimental/search/by-range/{propertyName}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyExperimental> experimentalPropertyByRange(@PathVariable("propertyName") String propertyName, @PathVariable("start") Double start, @PathVariable("end") Double end);

	/**
	 * {@code GET  chemical/property/experimental/name : get list of all experimental property names (Physchem).
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of all experimental property names (Physchem)}.
	 */
	@Operation(summary = "Get all experimental property options")
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
    @Operation(summary = "Get experimental properties for a batch of DTXSIDs", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyExperimental.class}))),
            @ApiResponse(responseCode = "400", description = "User has submitted more than allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports requests of '200' DTXSIDs at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/experimental/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyExperimental> experimentalBatchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifiers",
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
	@Operation(summary = "Get predicted properties by DTXSID",
           description = "return predicted properties for given DTXSID")
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
	@Operation(summary = "Get predicted properties by property and range",
            description = "return predicted properties by specifying the propertyName, start, and end values.")
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
	@Operation(summary = "Get all predicted property options")
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
    @Operation(summary = "Get predicted properties for a batch of DTXSIDs", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyPredicted.class}))),
            @ApiResponse(responseCode = "400", description = "User has submitted more than allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/predicted/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedBatchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifiers",
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
	@Operation(summary = "Get summary by DTXSID",
           description = "return property summary for given DTXSID")
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
	@Operation(summary = "Get summary by DTXSID and property",
           description = "return property summary for given DTXSID and property")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummary.class})))
   })
    @GetMapping(value = "chemical/property/summary/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummary> propertySummaryByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);
    
	/**
	 * {@code GET  chemical/property/summary/experimental/search/ : get list of individual property value summaries for the "dtxsid" and "propertyName".
	 *
	 * @param dtxsid the matching dtxsid of the property summaries to retrieve.
	 * @param propertyName the matching propertyName of the property summaries to retrieve.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of property summaries}.
	 */
	@Operation(summary = "Get experimental summary by DTXSID and property",
           description = "return experimental property summary for given DTXSID and property")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummaryExperimental.class})))
   })
    @GetMapping(value = "chemical/property/summary/experimental/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummaryExperimental> propertySummaryExperimentalByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);
	
	/**
	 * {@code GET  chemical/property/summary/predicted/search/ : get list of individual property value summaries for the "dtxsid" and "propertyName".
	 *
	 * @param dtxsid the matching dtxsid of the property summaries to retrieve.
	 * @param propertyName the matching propertyName of the property summaries to retrieve.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of property summaries}.
	 */
	@Operation(summary = "Get predicted property summary by DTXSID and property",
           description = "return predicted property summary for given DTXSID and property")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummaryExperimental.class})))
   })
    @GetMapping(value = "chemical/property/summary/predicted/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummaryPredicted> propertySummaryPredictedByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);
	
    // *********************** Property Summary - end *************************************
    // *********************** Fate - Start *************************************
    
	/**
	 * {@code GET  chemical/fate/search/by-dtxsid/{dtxsid} : get list of chemical fate (Env. Fate/transport) for the "dtxsid".
	 *
	 * @param dtxsid the matching dtxsid of the chemical fate (Env. Fate/transport) to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical fates (Env. Fate/transport)}.
	 */
	@Operation(summary = "Get fate data by DTXSID",
            description = "return environmental fate and transport data for given DTXSID")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalFateAllDto.class})))
    })
    @GetMapping(value = "chemical/fate/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ChemicalFateAllDto> fateByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
	/**
	 * {@code POST  chemical/fate/search/by-dtxsid/ : get list of chemical fates for the batch of "dtxsids".
	 *
	 * @param BatchRequest the matching dtxsid of the chemical fates to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical fates}.
	 */
    @Operation(summary = "Get fate data for a batch of DTXSIDs", description = "return environmental fate and transport data for given DTXSID. Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalFateBatchDto.class}))),
            @ApiResponse(responseCode = "400", description = "User has submitted more than allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports requests of '200' DTXSID at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    }) 
    @PostMapping(value = "chemical/fate/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalFateBatchDto> fateBatchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
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
	@Operation(summary = "Get fate summary by DTXSID",
           description = "return environmental fate and transport summary data for given DTXSID.")
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
	@Operation(summary = "Get fate summary by DTXSID and property",
           description = "return environmental fate and transport data for given DTXSID and property")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummary.class})))
   })
    @GetMapping(value = "chemical/fate/summary/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummary> fateSummaryByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);
	
	/**
	 * {@code GET  chemical/fate/summary/experimental/search/ : get list of individual property value summaries for the "dtxsid" and "propertyName".
	 *
	 * @param dtxsid the matching dtxsid of the property summaries to retrieve.
	 * @param propertyName the matching propertyName of the property summaries to retrieve.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of property summaries}.
	 */
	@Operation(summary = "Get experimental fate summary by DTXSID and property",
           description = "return experimental environmental fate and transport data for given DTXSID and property")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummaryExperimental.class})))
   })
    @GetMapping(value = "chemical/fate/summary/experimental/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummaryExperimental> fateSummaryExperimentalByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);
	
	/**
	 * {@code GET  chemical/fate/summary/predicted/search/ : get list of individual property value summaries for the "dtxsid" and "propertyName".
	 *
	 * @param dtxsid the matching dtxsid of the property summaries to retrieve.
	 * @param propertyName the matching propertyName of the property summaries to retrieve.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of property summaries}.
	 */
	@Operation(summary = "Get predicted fate summary by DTXSID and property",
           description = "return predicted environmental fate and transport data for given DTXSID and property")
   @ApiResponses(value= {
           @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                   schema=@Schema(oneOf = {ChemicalPropertySummaryExperimental.class})))
   })
    @GetMapping(value = "chemical/fate/summary/predicted/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertySummaryPredicted> fateSummaryPredictedByDtxsidAndName(@RequestParam(value ="dtxsid", required = true) String dtxsid,
    															@RequestParam(value ="propName", required = true) String propName);

}
