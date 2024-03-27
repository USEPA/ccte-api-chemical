package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import gov.epa.ccte.api.chemical.web.rest.requests.ChemicalListsAndDtxsids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link ChemicalList}s.
 */
@Tag(name = "Chemical List Resource",
        description = "API endpoints for getting chemical lists and chemicals in those lists.")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
public class ChemicalListResource {

    final private ChemicalListRepository listRepository;
    final private ChemicalListChemicalRepository chemicalListChemicalRepository;
    final private SearchChemicalService chemicalService;
    public ChemicalListResource(ChemicalListRepository repository, ChemicalListChemicalRepository chemicalListChemicalRepository, SearchChemicalService chemicalService) {
        this.listRepository = repository;
        this.chemicalListChemicalRepository = chemicalListChemicalRepository;
        this.chemicalService = chemicalService;
    }

    /**
     * {@code GET  /chemical/list/ : get list of chemical lists.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get all public lists")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @RequestMapping(value = "chemical/list/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List listAll(@RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection) {
        //return listRepository.findAll();
        return switch (projection) {
            case chemicallistall ->
                    listRepository.findByVisibilityAndIsVisibleOrderByTypeAscListNameAsc("PUBLIC",true, ChemicalListAll.class);
            case chemicallistname ->
                    listRepository.findByVisibilityAndIsVisibleOrderByTypeAscListNameAsc("PUBLIC", true, ChemicalListName.class);
            case chemicallistwithdtxsids ->
                    listRepository.getListsWithDtxsids("PUBLIC");
            default -> null;
        };
    }

    /**
     * {@code GET  /chemical/list/type : get all types.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list types}.
     */
    @Operation(summary = "Get all list types")
    @RequestMapping(value = "chemical/list/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> getAllType(){

        return listRepository.getAllTypes();
    }

    /**
     * {@code GET  /chemical/list/search : get list of chemical lists.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get public lists by type")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @RequestMapping(value = "chemical/list/search/by-type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List listByType( @Parameter(required = true, description = "Chemical List Type", example = "other") @PathVariable String type,
                                      @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection
                                      ){
        return switch (projection) {
            case chemicallistall -> listRepository.findByTypeAndVisibilityAndIsVisibleOrderByListNameAsc(type, "PUBLIC", true, ChemicalListAll.class);
            case chemicallistname -> listRepository.findByTypeAndVisibilityAndIsVisibleOrderByListNameAsc(type,"PUBLIC", true, ChemicalListName.class);
            case chemicallistwithdtxsids -> listRepository.getListsWithDtxsidsByType(type, "PUBLIC");
            default -> null;
        };
    }

    /**
     * {@code GET  /chemical/list/by-dtxsid/{dtxsid} : get list of chemical lists names.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists name}.
     */
    @Operation(summary = "Get public list by name")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @RequestMapping(value = "chemical/list/search/by-name/{listName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ChemicalListBase listByName(@Parameter(required = true, description = "Chemical List Name", example = "40CFR1164") @PathVariable String listName,
                                @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection){

        log.debug("list name={}", listName);

        return switch (projection) {
            case chemicallistall -> listRepository.findByListNameIgnoreCaseAndVisibilityAndIsVisible(listName, "PUBLIC",true, ChemicalListAll.class)
                    .orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            case chemicallistname -> listRepository.findByListNameIgnoreCaseAndVisibilityAndIsVisible(listName,"PUBLIC",true, ChemicalListName.class)
                    .orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            case chemicallistwithdtxsids -> listRepository.getListWithDtxsidsByListName(listName, "PUBLIC")
                    .orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            default -> null;
        };
    }

//    private Optional<ChemicalListWithDtxsids> getChemicalListWithDtxsids(String listName) {
//        return listRepository.getListWithDtxsidsByListName(listName);
//    }

    /**
     * {@code GET  chemical/list/search/by-dtxsid/ : get chemical lists names.
     * @param dtxsid return chemical list name where this chemical is present.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list names}.
     */
    @Operation(summary = "Get lists names by dtxsid")
    @RequestMapping(value = "chemical/list/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List listByDtxsid( @Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID1020560") @PathVariable String dtxsid,
                               @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection){

        log.debug("dtxsid={}, projection={}", dtxsid, projection);

        return switch (projection){
            case chemicallistname -> chemicalListChemicalRepository.getListNames(dtxsid, "PUBLIC");
            case chemicallistall -> listRepository.getListsByDtxsid(dtxsid, "PUBLIC");
           // case chemicalListwithdtxsids -> listRepository.getListsByDtxsidWithDtxsids(dtxsid, "PUBLIC");
            default -> null;
        };
       // return chemicalListChemicalRepository.getListNames(dtxsid);
    }


    @RequestMapping(value = "chemical/list/chemicals/search/start-with/{list}/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> startWith(@PathVariable String list, @PathVariable String word){

        log.debug("list={}, search word={}", list, word);

        String searchWord = chemicalService.preprocessingSearchWord(word);

        return chemicalListChemicalRepository.startWith(searchWord, list);

    }

    @RequestMapping(value = "chemical/list/chemicals/search/contain/{list}/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> contain(@PathVariable String list, @PathVariable String word){

        log.debug("list={}, search word={}", list, word);

        String searchWord = chemicalService.preprocessingSearchWord(word);

        return chemicalListChemicalRepository.contain(searchWord, list);

    }

    @RequestMapping(value = "chemical/list/chemicals/search/equal/{list}/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> exact(@PathVariable String list, @PathVariable String word){

        log.debug("list={}, search word={}", list, word);

        String searchWord = chemicalService.preprocessingSearchWord(word);

        return chemicalListChemicalRepository.exact(searchWord, list);

    }

    @Operation(hidden = true)
    @RequestMapping(value = "chemical/list/chemicals/search/by-dtxsid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> contain(@RequestBody ChemicalListsAndDtxsids request){

        log.debug("dtxsids = {}, chemical lists = {}", request.getDtxsids().size(), request.getChemicalLists().size());

        List<String> result = chemicalListChemicalRepository.chemicalListsAndDtxsids(request.getChemicalLists(), request.getDtxsids());

        return result;

    }
}
