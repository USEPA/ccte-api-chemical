package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ModelFiles;
import gov.epa.ccte.api.chemical.domain.ModelReports;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

/**
 * REST controller for getting the {@link ModelFiles, @link ModelReports}s.
 */
@Hidden
@Tag(name = "Chemical Property Model Resource",
        description = "API endpoints for collecting data for given property model reports and files).")
@SecurityRequirement(name = "api_key")
@RequestMapping( value = "chemical/property/model")
public interface ChemicalPropertyModelApi {

    @Operation(summary = "Find chemical property model reports by dtxsid", description = "return model reports for requested dtxsid")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ModelReports.class}))),
    })
    @RequestMapping(value = "/reports/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List getModelReportByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020005") @PathVariable("dtxsid") String dtxsid);

    @Operation(summary = "Find chemical property model files by model and type ID", description = "return model files for requested model and type ID")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "image/png",
                    schema=@Schema(oneOf = {ModelFiles.class}))),
    })

    @RequestMapping(value = "/file/search/", method = RequestMethod.GET)
    ResponseEntity<byte[]> getModelFileByModelIdAndTypeId(@RequestParam(value = "modelId") Integer modelId,
    														@RequestParam(value = "typeId") Integer typeId);

    @Operation(summary = "Find chemical property model HTML by dtxsid and model id", description = "return model html for requested dtxsid and model id")
    @RequestMapping(value = "/reports/html/search/", method = RequestMethod.GET)
    String getModelHTMLByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020005") String dtxsid,
    								  @Parameter(required = true, description = "model id", example = "1163") Long modelId);

    @Operation(summary = "Find chemical property model reports by dtxsid", description = "return model reports for requested dtxsid")

    @RequestMapping(value = "/reports/json/search/", method = RequestMethod.GET)
    String getModelJSONByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020005") String dtxsid,
									  @Parameter(required = true, description = "model id", example = "1163") Long modelId);

}
