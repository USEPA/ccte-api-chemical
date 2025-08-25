package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ExtraData;
import gov.epa.ccte.api.chemical.repository.ExtraDataRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link ExtraData}s.
 */
@Tag(name = "Extra Data Resource",
        description = "API endpoints for returning extra data relating to chemical(s) identified by DTXSID(s)")
@SecurityRequirement(name = "api_key")
@RequestMapping( value = "chemical/extra-data", produces = MediaType.APPLICATION_JSON_VALUE)

@Slf4j
@RestController
public class ExtraDataResource {
    final private ExtraDataRepository repository;

    @Value("2000")
    private Integer batchSize;

    public ExtraDataResource(ExtraDataRepository repository) {
        this.repository = repository;
    }

    /**
     * {@code GET  /chemical/extra-data/by-dtxsid/:dtxsid} : get list of extra chemical for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the extraData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of ExtraData.
     */
    @Operation(summary = "Get data by dtxsid",
            description = "Specify the dtxsid as part of the path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ExtraData.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found.",
                    content = @Content(mediaType = "application/problem+json",
                            examples = {@ExampleObject(value = "{\"title\":\"Bad Request\",\"status\":400,\"detail\":\"dtxsid with value (${application.dtxsid}) not found.\",\"instance\":[\"/chemical/extra-data/search/by-dtxsid/($application.dtxsid})\"]}", description = "Here response is with suggestion for 'caffiene'")},
                            schema = @Schema(oneOf = {ExtraData.class})))
    })
    @RequestMapping(value = "/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ExtraData> extraDataByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID101296374") @PathVariable("dtxsid") String dtxsid) {
        log.debug("dtxsid = {}", dtxsid);

        List<ExtraData> data = repository.findByDtxsid(dtxsid, ExtraData.class);
        if (data.isEmpty())
            throw new IdentifierNotFoundException("dtxsid", dtxsid);
        else
            return data;
    }

    /**
     * {@code POST  /chemical/extra-data/by-dtxsid/} : get list of extra chemical for batch of "dtxsids".
     *
     * @param dtxsids the matching dtxsids of the extraData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of ExtraData.
     */
    @Operation(summary = "Get data by dtxsid",
            description = "Specify the dtxsid as part of the path.")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ExtraData.class})))
    })
    @RequestMapping(value = "/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ExtraData> batchSearchExtraData(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
        content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
             examples = {@ExampleObject("\"[\\\"DTXSID101296374\\\",\\\"DTXSID10612113\\\",\\\"DTXSID20635878\\\"]\"")})})
                                        @RequestBody String[] dtxsids){
        if(dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsids");

        List<ExtraData> data = repository.findByDtxsidInOrderByDtxsidAsc(dtxsids, ExtraData.class);

        return data;
    }
}