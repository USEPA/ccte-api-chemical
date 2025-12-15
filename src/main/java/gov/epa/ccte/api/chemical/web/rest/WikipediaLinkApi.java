package gov.epa.ccte.api.chemical.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API interface for checking availability of data in Wikipedia for GHS Safety data
 */
@Tag(name = "Wikipedia to GHS Classification Resource",
        description = "Collection of endpoints to check if GHS classification safety data is available on Wikipedia.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/wikipedia")
public interface WikipediaLinkApi {

    @Operation(summary = "Check for link by DTXSID", description = "This endpoint will return Y if Wikipedia has GHS Safety data, otherwise N.")
    @GetMapping(value = "/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    WikipediaLinkResponse byDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);

    @Operation(summary = "Check for link for a batch of DTXSIDs", description = "This endpoint will return Y if Wikipedia has GHS Safety data, otherwise N.")
    @PostMapping(value = "/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<WikipediaLinkResponse> byBatchDtxsid(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifiers",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                                        @RequestBody String[] dtxsids);
}
