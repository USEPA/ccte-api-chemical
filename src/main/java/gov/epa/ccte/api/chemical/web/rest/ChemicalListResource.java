package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
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
    public List<?> listAll(ChemicalListProjection projection) {
        return switch (projection) {
            case chemicallistall -> listRepository.findAllOrderByTypeAscAndListNameAsc(ChemicalList.class);
            case chemicallistname -> listRepository.findAllOrderByListNameAsc(ChemicalListName.class);
            case chemicallistwithdtxsids -> listRepository.getListsWithDtxsids();
            default -> null;
        };
    }

    @Override
    public List<String> getAllType() {
        return listRepository.getAllTypes();
    }

    @Override
    public List<?> listByType(String type, ChemicalListProjection projection) {
        return switch (projection) {
            case chemicallistall -> listRepository.findByTypeIgnoreCaseOrderByListNameAsc(type, ChemicalList.class);
            case chemicallistname -> listRepository.findByTypeIgnoreCaseOrderByListNameAsc(type, ChemicalListName.class);
            case chemicallistwithdtxsids -> listRepository.getListsWithDtxsidsByType(type);
            default -> null;
        };
    }

    @Override
    public ChemicalListBase listByName(String listName, ChemicalListProjection projection) {
        log.debug("list name={}", listName);
        return switch (projection) {
            case chemicallistall -> listRepository.findByListNameIgnoreCase(listName).orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            case chemicallistwithdtxsids -> listRepository.getListWithDtxsidsByListName(listName).orElseThrow(() -> new IdentifierNotFoundException("List name", listName));
            default -> null;
        };
    }

    @Override
    public List<?> listByDtxsid(String dtxsid, ChemicalListProjection projection) {
        log.debug("dtxsid={}, projection={}", dtxsid, projection);
        return switch (projection) {
            case chemicallistname -> listRepository.getListNamesByDtxsid(dtxsid);
            case chemicallistall -> listRepository.getListsByDtxsid(dtxsid);
            case ccdchemicaldetaillists -> listRepository.getListsByDtxsidCcd(dtxsid);
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