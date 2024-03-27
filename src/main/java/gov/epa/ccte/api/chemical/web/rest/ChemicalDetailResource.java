package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.*;
import gov.epa.ccte.api.chemical.service.ChemicalDetailService;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfDtxsidException;
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
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link ChemicalDetail}s.
 */
@Tag(name = "Chemical Details Resource",
        description = "API endpoints for collecting data for given chemical(s).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
public class ChemicalDetailResource {
    final private ChemicalDetailService detailService;

    @Value("${application.batch-size}")
    private Integer batchSize;

    public ChemicalDetailResource(ChemicalDetailService detailService) {
        this.detailService = detailService;
    }

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
    @RequestMapping(value = "chemical/detail/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ChemicalDetailBase detailByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                      @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection) {

        log.debug("dtxsid = {}", dtxsid);

        //return getChemicalDetail(dtxsid, "dtxsid", projection);

        List data = getChemicalDetails(new String[]{dtxsid}, "dtxsid", projection);

        if(data.isEmpty())
            throw  new IdentifierNotFoundException("dtxsid", dtxsid);
        else
            return (ChemicalDetailBase) data.get(0);
    }

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
    @RequestMapping(value = "chemical/detail/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ChemicalDetailBase detailsByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505")  @PathVariable("dtxcid") String dtxcid,
                           @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection) {

        log.debug("dtxcid = {}", dtxcid);

        List data = getChemicalDetails(new String[]{dtxcid}, "dtxcid", projection);

        if(data.isEmpty())
            throw  new IdentifierNotFoundException("dtxcid", dtxcid);
        else
            return (ChemicalDetailBase) data.get(0);
    }

    /**
     * {@code POST  /chemical/detail/search/ : get list of chemicalDetail for the multiple "dtxsid".
     * @param BatchRequest the matching dtxcid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get data by the batch of dtxsids",
            description = "Besides batch of the values, the user can also define projection (set of attributes to return). Note: Maximum 200 DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/problem+json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @RequestMapping(value = "chemical/detail/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List batchSearch( @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
                        examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                      @RequestBody String[] dtxsids,
                      @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall")
                      ChemicalDetailProjection projection) {

        log.debug("dtxsids = {}", dtxsids.length);

        if(dtxsids.length > batchSize)
            throw new HigherNumberOfDtxsidException(dtxsids.length, batchSize);

        List data =  getChemicalDetails(dtxsids, "dtxsid", projection);
        log.debug("database return {}", data.size());

        return data;
    }

    private List getChemicalDetails(String[] dtxsids, String type, ChemicalDetailProjection projection) {
        return switch (projection) {
            case chemicaldetailall ->
                    detailService.getChemicalDetailsForBatch(dtxsids, ChemicalDetailAll.class, type);
            case chemicaldetailstandard ->
                    detailService.getChemicalDetailsForBatch(dtxsids, ChemicalDetailStandard.class, type);
            case chemicalidentifier ->
                    detailService.getChemicalDetailsForBatch(dtxsids, ChemicalIdentifier.class, type);
            case chemicalstructure ->
                    detailService.getChemicalDetailsForBatch(dtxsids, ChemicalStructure.class, type);
            case ntatoolkit -> detailService.getChemicalDetailsForBatch(dtxsids, NtaToolkit.class, type);
            case ccdchemicaldetails -> detailService.getChemicalDetailsForBatch(dtxsids, CcdChemicalDetails.class, type);
        };
    }

    /**
     * {@code GET  /chemical/detail/search/by-listname/:listname} : get list of chemicalDetail for the "listname".
     *
     * @param listname chemicals defined in this list will return with chemical details.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     * chemicaldetailall, chemicaldetailstandard, chemicalidentifier, chemicalstructure, ntatoolkit
     */
//    @Operation(summary = "Get data by listname",
//            description = "Specify the listname as part of the path, and optionally user can also define projection (set of attributes to return).")
//    @ApiResponses(value= {
//            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
//                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class, ChemicalDetailAll.class, NtaToolkit.class})))
//    })
//    @RequestMapping(value = "chemical/detail/search/by-listname/{listname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    List detailsByListname(@Parameter(required = true, description = "Chemical list name", example = "ACSREAG")  @PathVariable("listname") String listname,
//                                       @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection) {
//
//        log.debug("listname = {}", listname);
//
//        return switch (projection) {
//            case chemicaldetailall -> detailService.getChemicalDetailsForListName(listname, ChemicalDetailAll.class);
//            case chemicaldetailstandard ->
//                    detailService.getChemicalDetailsForListName(listname, ChemicalDetailStandard.class);
//            case chemicalidentifier -> detailService.getChemicalDetailsForListName(listname, ChemicalIdentifier.class);
//            case chemicalstructure -> detailService.getChemicalDetailsForListName(listname, ChemicalStructure.class);
//            case ntatoolkit -> detailService.getChemicalDetailsForListName(listname, NtaToolkit.class);
//            case ccdchemicaldetails -> detailService.getChemicalDetailsForListName(listname, CcdChemicalDetails.class);
//        };
//    }
}


