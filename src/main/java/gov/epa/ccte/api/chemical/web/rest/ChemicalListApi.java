package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.web.rest.requests.ChemicalListsAndDtxsids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API interface for retrieving chemical lists.
 */
@Tag(name = "Chemical List Resource",
        description = "Collection of endpoints for chemical lists. This curated data is sourced from the US EPA's Distributed Structure-Searchable Toxicity (DSSTox) database.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/list", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ChemicalListApi {

    @Operation(summary = "Get all lists")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalList.class, ChemicalListName.class})))
    })
    @GetMapping(value = "/all")
    List<?> listAll(@RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get all list types")
    @GetMapping(value = "/type")
    List<String> getAllType();

    @Operation(summary = "Get lists by list type")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalList.class, ChemicalListName.class})))
    })
    @GetMapping(value = "/search/by-type/{type}")
    List<?> listByType(@Parameter(required = true, description = "Chemical List Type", example = "other") @PathVariable String type,
                    @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get lists by name")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalList.class, ChemicalListWithDtxsids.class})))
    })
    @GetMapping(value = "/search/by-name/{listName}")
    ChemicalListBase listByName(@Parameter(required = true, description = "Chemical List Name", example = "40CFR1164") @PathVariable String listName,
                                @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get lists containing DTXSID")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalList.class, ChemicalListName.class})))
    })
    @GetMapping(value = "/search/by-dtxsid/{dtxsid}")
    List<?> listByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID1020560") @PathVariable String dtxsid,
                      @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get DTXSIDs for list and containing starting string in chemical name")
    @GetMapping(value = "/chemicals/search/start-with/{list}/{word}")
    List<String> startWith(@Parameter(required = true, description = "List Name", example = "40CFR1164") @PathVariable String list, 
    						@Parameter(required = true, description = "Chemical Name", example = "ammo") @PathVariable String word);

    @Operation(summary = "Get DTXSIDs for list and containing substring in chemical name")
    @GetMapping(value = "/chemicals/search/contain/{list}/{word}")
    List<String> contain(@Parameter(required = true, description = "List Name", example = "ANTMIC") @PathVariable String list, 
							@Parameter(required = true, description = "Chemical Name", example = "PHOSPHORIC ACID") @PathVariable String word);

    @Operation(summary = "Get DTXSIDs for list and containing exact string in chemical name")
    @GetMapping(value = "/chemicals/search/equal/{list}/{word}")
    List<String> exact(@Parameter(required = true, description = "List Name", example = "ANTMIC") @PathVariable String list, 
						@Parameter(required = true, description = "Chemical Name", example = "PHOSPHORIC ACID") @PathVariable String word);

    @Operation(summary = "Get DTXSIDs by list")
    @GetMapping(value = "/chemicals/search/by-listname/{list}")
    List<String> listDtxsids(@PathVariable String list);

    @Operation(hidden = true)
    @PostMapping(value = "/chemicals/search/by-dtxsid")
    List<String> contain(@RequestBody ChemicalListsAndDtxsids request);
}
