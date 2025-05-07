package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import gov.epa.ccte.api.chemical.web.rest.requests.ChemicalListsAndDtxsids;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * REST controller for retrieving chemical lists.
 */
@Slf4j
@RestController
public class ChemicalListResource implements ChemicalListApi {

    private final ChemicalListRepository listRepository;
    private final ChemicalListChemicalRepository chemicalListChemicalRepository;
    private final SearchChemicalService chemicalService;

    public ChemicalListResource(ChemicalListRepository repository, ChemicalListChemicalRepository chemicalListChemicalRepository, SearchChemicalService chemicalService) {
        this.listRepository = repository;
        this.chemicalListChemicalRepository = chemicalListChemicalRepository;
        this.chemicalService = chemicalService;
    }

    @Override
    public List listAll(ChemicalListProjection projection) {
        return switch (projection) {
            case chemicallistall -> listRepository.findByVisibilityAndIsVisibleOrderByTypeAscListNameAsc("PUBLIC", true, ChemicalListAll.class);
            case chemicallistname -> listRepository.findByVisibilityAndIsVisibleOrderByTypeAscListNameAsc("PUBLIC", true, ChemicalListName.class);
            case chemicallistwithdtxsids -> listRepository.getListsWithDtxsids("PUBLIC");
            default -> null;
        };
    }

    @Override
    public List<String> getAllType() {
        return listRepository.getAllTypes();
    }

    @Override
    public List listByType(String type, ChemicalListProjection projection) {
        return switch (projection) {
            case chemicallistall -> listRepository.findByTypeAndVisibilityAndIsVisibleOrderByListNameAsc(type, "PUBLIC", true, ChemicalListAll.class);
            case chemicallistname -> listRepository.findByTypeAndVisibilityAndIsVisibleOrderByListNameAsc(type, "PUBLIC", true, ChemicalListName.class);
            case chemicallistwithdtxsids -> listRepository.getListsWithDtxsidsByType(type, "PUBLIC");
            default -> null;
        };
    }

    @Override
    public ChemicalListBase listByName(String listName, ChemicalListProjection projection) {
        log.debug("list name={}", listName);
        return switch (projection) {
            case chemicallistall -> listRepository.findByListNameIgnoreCaseAndVisibilityAndIsVisible(listName, "PUBLIC", true, ChemicalListAll.class)
                    .orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            case chemicallistname -> listRepository.findByListNameIgnoreCaseAndVisibilityAndIsVisible(listName, "PUBLIC", true, ChemicalListName.class)
                    .orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            case chemicallistwithdtxsids -> listRepository.getListWithDtxsidsByListName(listName, "PUBLIC")
                    .orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            default -> null;
        };
    }

    @Override
    public List listByDtxsid(String dtxsid, ChemicalListProjection projection) {
        log.debug("dtxsid={}, projection={}", dtxsid, projection);
        List<String> chemicalLists = chemicalListChemicalRepository.getListNames(dtxsid, "PUBLIC");
        return switch (projection) {
            case chemicallistname -> listRepository.findByListNameInIgnoreCaseAndVisibilityAndIsVisibleOrderByListNameAsc(chemicalLists, "PUBLIC", true, ChemicalListName.class);
            case chemicallistall -> listRepository.getListsByDtxsid(dtxsid, "PUBLIC");
            case ccdchemicaldetaillists -> listRepository.getListsByDtxsid(dtxsid, "PUBLIC");
            default -> null;
        };
    }

    @Override
    public List<String> startWith(String list, String word) {
        log.debug("list={}, search word={}", list, word);
        String searchWord = chemicalService.preprocessingSearchWord(word);
        return chemicalListChemicalRepository.startWith(searchWord, list);
    }

    @Override
    public List<String> contain(String list, String word) {
        log.debug("list={}, search word={}", list, word);
        String searchWord = chemicalService.preprocessingSearchWord(word);
        return chemicalListChemicalRepository.contain(searchWord, list);
    }

    @Override
    public List<String> exact(String list, String word) {
        log.debug("list={}, search word={}", list, word);
        String searchWord = chemicalService.preprocessingSearchWord(word);
        return chemicalListChemicalRepository.exact(searchWord, list);
    }

    @Override
    public List<String> listDtxsids(String list) {
        log.debug("list={}", list);
        return chemicalListChemicalRepository.getDtxsids(list);
    }

    @Override
    public List<String> contain(ChemicalListsAndDtxsids request) {
        log.debug("dtxsids = {}, chemical lists = {}", request.getDtxsids().size(), request.getChemicalLists().size());
        List<String> result = chemicalListChemicalRepository.chemicalListsAndDtxsids(request.getChemicalLists(), request.getDtxsids());
        log.info("result.size={}", result.size());
        return result;
    }
}