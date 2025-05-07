package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;
import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import gov.epa.ccte.api.chemical.projection.search.ChemicalBatchSearchResult;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Chemical Search Resource", description = "API endpoints for searching chemicals using different identifiers or characteristics.")
@SecurityRequirement(name = "api_key")
public interface ChemicalSearchApi{
	
    @Operation(summary = "Search by starting value", description = "NOTE: Search value needs to be URL encoded for synonyms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ChemicalSearchAll.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found.",
                    content = @Content(mediaType = "application/problem+json", schema = @Schema(oneOf = {ProblemDetail.class})))
    })
    @GetMapping(value = "chemical/search/start-with/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalSearchAll> chemicalStartWith(
            @Parameter(required = true, description = "Starting characters for search word",
                    examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182"),
                            @ExampleObject(name="CASRN", value = "1912-24")})
            @PathVariable("word") String word,
            @RequestParam(value = "top", required = false, defaultValue = "500") Integer top);
    
    @Operation(summary = "Search by exact value", description = "NOTE: Search value needs to be URL encoded for synonyms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ChemicalSearchAll.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found.",
                    content = @Content(mediaType = "application/problem+json", schema = @Schema(oneOf = {ProblemDetail.class})))
    })
    @GetMapping(value = "chemical/search/equal/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    List chemicalEqual(
            @Parameter(required = true, description = "Exact match of search word",
                    examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182"),
                            @ExampleObject(name="CASRN", value = "1912-24-9")})
            @PathVariable("word") String word,
            @RequestParam(value = "projection", required = false, defaultValue = "chemicalsearchall") String projection);
    
    @Operation(summary = "Search by substring value", description = "NOTE: Search value needs to be URL encoded for synonyms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ChemicalSearchAll.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found.",
                    content = @Content(mediaType = "application/problem+json", schema = @Schema(oneOf = {ProblemDetail.class})))
    })
    @GetMapping(value = "chemical/search/contain/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    List chemicalContain(
            @Parameter(required = true, description = "Substring of search word",
                    examples = {@ExampleObject(name="DSSTox Compound Identifier", value = "DTXCID505"),
                            	@ExampleObject(name="Synonym", value = "razine")})
            @PathVariable("word") String word,
            @RequestParam(value = "top", required = false, defaultValue = "0") Integer top,
            @RequestParam(value = "projection", required = false, defaultValue = "chemicalsearchall") String projection);
    
    @Operation(summary = "Search ms ready chemicals by formula")
    @GetMapping(value = "chemical/msready/search/by-formula/{formula}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> msReadyByFormula(@Parameter(required = true, description = "Chemical formula", example = "C16H24N2O5S") @PathVariable("formula") String formula);
    
    @Operation(summary = "Search ms ready chemicals by DTXCID")
    @GetMapping(value = "chemical/msready/search/by-dtxcid/{dtxcid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> msReadyByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID30182") @PathVariable("dtxcid") String dtxcid);

    @Operation(summary = "Search ms ready chemicals by bacth of DTXCIDs")
    @PostMapping(value = "chemical/msready/search/by-dtxcid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List msReadyByBatchDtxcid(@RequestBody String[] dtxcids);

    @Operation(summary = "Search ms ready chemicals using mass range")
    @GetMapping(value = "chemical/msready/search/by-mass/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> msReadyByMass(@Parameter(required = true, description = "Starting mass value", example = "200.90") @PathVariable("start") Double start,
                               @Parameter(required = true, description = "Ending mass value", example = "200.95") @PathVariable("end") Double end);
    
    @Operation(summary = "Search ms ready chemicals using bacth of mass ranges")
    @PostMapping(value = "chemical/msready/search/by-mass/", produces = MediaType.APPLICATION_JSON_VALUE)
    HashMap<Double, List<String>> msReadyByBatchMass(@RequestBody BatchMsReadyMassForm form);
    
    @Operation(summary = "Search chemicals by exact formula")
    @GetMapping(value = "chemical/search/by-exact-formula/{formula}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getChemicalsForExactFormula(@Parameter(required = true, description = "Chemical formula", example = "C15H16O2") @PathVariable("formula") String formula);
    
    @Operation(summary = "Search chemicals Count by exact formula")
    @GetMapping(value = "chemical/count/by-exact-formula/{formula}")
    Long getChemicalsCountForExactFormula(@Parameter(required = true, description = "Chemical formula", example = "C15H16O2") @PathVariable("formula") String formula,
                                          @RequestParam(value = "projection", required = false, defaultValue = "count") String projection);
    
    @Operation(summary = "Search chemicals by ms ready formula")
    @GetMapping(value = "chemical/search/by-msready-formula/{formula}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getChemicalsForMsreadyFormula(@Parameter(required = true, description = "Chemical formula", example = "C15H16O2") @PathVariable("formula") String formula);
    
    @Operation(summary = "Search chemicals Count by ms ready formula")
    @GetMapping(value = "chemical/count/by-msready-formula/{formula}")
    Long getChemicalsCountForMsreadyFormula(@Parameter(required = true, description = "Chemical formula", example = "C15H16O2") @PathVariable("formula") String formula,
                                            @RequestParam(value = "projection", required = false, defaultValue = "count") String projection);
    
    @Operation(summary = "Search by exact batch of values", description = "NOTE: Search batch of values (values are separated by EOL character and maximum 200 values are allowed).")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ChemicalBatchSearchResult.class})))
    })
    
    @PostMapping(value = "chemical/search/equal/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalBatchSearchResult> chemicalBatchEqual(@RequestBody String words);

}
