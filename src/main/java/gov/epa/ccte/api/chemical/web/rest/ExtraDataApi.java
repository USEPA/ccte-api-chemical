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

import gov.epa.ccte.api.chemical.domain.ExtraData;

import java.util.List;

/**
 * API interface for retrieving extra chemical data.
 */
@Tag(name = "Lit Search Data Resource",
        description = "Collection of endpoints containing extra chemical data pertaining to literature and patents.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/extra-data", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ExtraDataApi {

    @Operation(summary = "Get data by DTXSID",
            description = "return data for given DTXSID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ExtraData.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found.",
                    content = @Content(mediaType = "application/problem+json",
                            examples = {@ExampleObject(value = "{\"title\":\"Bad Request\",\"status\":400,\"detail\":\"dtxsid with value (${application.dtxsid}) not found.\",\"instance\":[\"/chemical/extra-data/search/by-dtxsid/($application.dtxsid})\"]}", description = "Here response is with suggestion for 'caffeine'")},
                            schema = @Schema(oneOf = {ExtraData.class})))
    })
    @GetMapping(value = "/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ExtraData> extraDataByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID101296374") @PathVariable("dtxsid") String dtxsid);

    @Operation(summary = "Get data for a batch of DTXSIDs",
            description = "return data for given DTXSID")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ExtraData.class})))
    })
    @PostMapping(value = "/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ExtraData> batchSearchExtraData(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifiers",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID101296374\\\",\\\"DTXSID10612113\\\",\\\"DTXSID20635878\\\"]\"")})})
                                        @RequestBody String[] dtxsids);
}