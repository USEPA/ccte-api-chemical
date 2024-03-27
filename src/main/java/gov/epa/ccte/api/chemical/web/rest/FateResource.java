package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.Fate;
import gov.epa.ccte.api.chemical.dto.mapper.FateMapper;
import gov.epa.ccte.api.chemical.projection.FateAll;
import gov.epa.ccte.api.chemical.repository.FateRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfDtxsidException;
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
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link Fate}s.
 */
@Tag(name = "Chemical Fate Resource",
        description = "API endpoints for getting chemical fate data for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
public class FateResource {

    final private FateRepository repository;
    final private FateMapper mapper;
    @Value("${application.batch-size}")
    private Integer batchSize;

    public FateResource(FateRepository repository, FateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@code GET  /fate/by-dtxsid/{dtxsid} : get list of fate data for the "dtxsid".
     * @param dtxsid the matching dtxsid of the fate data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fate}.
     */
    @Operation(summary = "Get data by dtxsid")
    @RequestMapping(value = "chemical/fate/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FateAll> fateByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182")
                               @PathVariable("dtxsid") String dtxsid) {

        log.debug("dtxsid = {}", dtxsid);

        return repository.findByDtxsid(dtxsid, FateAll.class);
    }

    /**
     * {@code POST  /chemical/fate/search/ : get list of chemical fate data for the multiple "dtxsid".
     * @param BatchRequest the matching dtxsid of the chemical fate data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get data by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {FateAll.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/problem+json",
                    examples = {@ExampleObject(name = "", value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @RequestMapping(value = "chemical/fate/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FateAll> batchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                              @RequestBody String[] dtxsids) {

        log.debug("dtxsids = {}", dtxsids.length);

        if(dtxsids.length > batchSize)
            throw new HigherNumberOfDtxsidException(dtxsids.length, batchSize);

        return repository.findByDtxsidInOrderByDtxsidAscEndpointNameAsc(dtxsids, FateAll.class);
    }
}
