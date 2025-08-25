package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.ccte.api.chemical.projection.search.ChemicalBatchSearchResult;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchInternal;
import gov.epa.ccte.api.chemical.projection.search.DtxsidOnly;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.errors.ChemicalSearchNotFoundException;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ChemicalSearchResource implements ChemicalSearchApi {
    @Value("${application.batch-size}")
    private Integer batchSize;
    
    private final ChemicalSearchRepository searchRepository;
    private final SearchChemicalService chemicalService;

    public ChemicalSearchResource(ChemicalSearchRepository searchRepository, SearchChemicalService chemicalService) {
        this.searchRepository = searchRepository;
        this.chemicalService = chemicalService;
    }
    
    @Override
    public List<ChemicalSearchAll> chemicalStartWith(String word, Integer top) {
        List<ChemicalSearchAll> searchResult = chemicalService.getStartWith(word, top);
        if (!searchResult.isEmpty()) {
            if (top != null && top > 0) {
                log.debug("picking up top {} records", top);
                return searchResult.stream().limit(top).collect(Collectors.toList());
            } else {
                return searchResult;
            }
        } else {
            throw new ChemicalSearchNotFoundException(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        }
    }
    
    @Override
    public List chemicalEqual(String word, String projection) {
        String searchWord = chemicalService.preprocessingSearchWord(word);
        log.debug("input search word = {} and process search word = {}. ", word, searchWord);
        List searchResult = null;
        switch (projection) {
            case "chemicalsearchall":
                searchResult = searchRepository.findByModifiedValueOrderByRankAsc(searchWord, ChemicalSearchAll.class);
                searchResult = chemicalService.removeDuplicates(searchResult);
                break;
            case "dtxsidonly":
                searchResult = searchRepository.findByModifiedValueOrderByRankAsc(searchWord, DtxsidOnly.class);
                searchResult = chemicalService.removeDuplicates(searchResult);
                break;
            case "ccdsearchresult":
                searchResult = searchRepository.equalCcd(searchWord);
                searchResult = chemicalService.removeDuplicates(searchResult);
                break;
        }
        if (searchResult == null || searchResult.isEmpty())
            throw new ChemicalSearchNotFoundException(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        else
            return searchResult;
    }
    @Override
    public List chemicalContain(String word, Integer top, String projection) {
        String searchWord = chemicalService.preprocessingSearchWord(word);
        log.debug("input search word = {} and process search word = {}. projection = {}", word, searchWord, projection);
        List searchResult = chemicalService.getContain(projection, searchWord, top);
        searchResult = chemicalService.removeDuplicates(searchResult);
        if (searchResult == null || searchResult.isEmpty())
            throw new ChemicalSearchNotFoundException(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        else
            return searchResult;
    }
    
    @Override
    public List<ChemicalBatchSearchResult> chemicalBatchEqual(String words) {
        String[] searchWords = chemicalService.preprocessingSearchWord(words.split("\n"));
        log.debug("input search words = {} and process search word count = {}. ", words, searchWords.length);
        List<ChemicalSearchInternal> searchResult = searchRepository.findByModifiedValueInOrderByRankAsc(List.of(searchWords), ChemicalSearchInternal.class);
        return chemicalService.processBatchResult(searchResult, searchWords);
    }
    
    @Override
    public List<String> msReadyByFormula(String formula) {
        log.debug("input formula = {} ", formula);
        return searchRepository.searchMsReadyFormula(formula);
    }
    
    @Override
    public List<String> msReadyByDtxcid(String dtxcid) {
        log.debug("input dtxcid = {} ", dtxcid);
        return searchRepository.searchMsReadyDtxcid(dtxcid);
    }
    
    @Override
    public List msReadyByBatchDtxcid(String[] dtxcids) {
        log.info("dtxcid size = {}", dtxcids.length);

        if (dtxcids.length > batchSize) {
            throw new HigherNumberOfIdsException(dtxcids.length, batchSize, "dtxcid");
        }

        return searchRepository.searchMsReadyByBatchDtxcid(dtxcids);
    }


    @Override
    public List<String> msReadyByMass(Double start, Double end) {
        log.debug("input mass = {} - {} ", start, end);
        return searchRepository.searchMsReadyMass(start, end);
    }
    
    @Override
    public HashMap<Double, List<String>> msReadyByBatchMass(BatchMsReadyMassForm form) {
        log.debug("input masses = {} error= {} ", form.getMasses(), form.getError());
        
        return chemicalService.getMsReadyBatchResult(form);
    }
    
    
    @Override
    public List<String> getChemicalsForExactFormula(String formula) {
        log.debug("exact formula search for {} ", formula);
        return searchRepository.getExactFormula(formula);
    }
    
    @Override
    public Long getChemicalsCountForExactFormula(String formula, String projection) {
        log.debug("exact formula count for {} ", formula);
        return searchRepository.getExactFormulaCount(formula);
    }
    
    @Override
    public List<String> getChemicalsForMsreadyFormula(String formula) {
        log.debug("input formula {} ", formula);
        return searchRepository.searchAllMsReadyFormula(formula);
    }
    
    @Override
    public Long getChemicalsCountForMsreadyFormula(String formula, String projection) {
        log.debug("formula count for {} ", formula);
        return searchRepository.getMsReadyFormulaCount(formula);
    }
    
}

