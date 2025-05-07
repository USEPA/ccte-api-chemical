package gov.epa.ccte.api.chemical.service;


import gov.epa.ccte.api.chemical.projection.search.*;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.web.rest.BatchMsReadyMassForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.regex.Pattern;

import static gov.epa.ccte.api.chemical.service.ChemicalUtils.*;

@Slf4j
@Service
public class SearchChemicalService {

    private final CaffeineFixSynonymService caffeineFixService;
    private final ChemicalSearchRepository searchRepository;
    private final OpsinService opsinService;
    private static final Pattern ENCODED_PATTERN = Pattern.compile("(%[0-9A-Fa-f]{2}|\\+)");
    private final List<String> isThisCASRN;
    private final List<String> searchMatchWithoutInchikey;
    private final List<String> searchMatchWithInchikey;
    private final List<String> searchNames4SingleSearch;


    public SearchChemicalService(CaffeineFixSynonymService caffeineFixService, ChemicalSearchRepository searchRepository, OpsinService opsinService) {
        this.caffeineFixService = caffeineFixService;
        this.searchRepository = searchRepository;
        this.opsinService = opsinService;
        // Initialize values
        isThisCASRN = Arrays.asList("Alternate CAS-RN","Integrated Source CAS-RN","CASRN","FDA CAS-Like Identifier","Deleted CAS-RN");
        searchMatchWithoutInchikey = Arrays.asList("Deleted CAS-RN","PC-Code","Substance_id","Approved Name","Alternate CAS-RN",
                "CAS-RN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
                "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id", "EHCA Number", "EC Number");
        searchMatchWithInchikey = Arrays.asList("Deleted CAS-RN","PC-Code","Substance_id","Approved Name","Alternate CAS-RN",
                "CAS-RN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
                "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id",
                "InChIKey", "Indigo InChIKey", "EHCA Number", "EC Number");
        searchNames4SingleSearch = Arrays.asList("Deleted CAS-RN","PC-Code","Approved Name","Alternate CAS-RN",
                "CASRN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
                "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id",
                "EHCA Number", "EC Number", "InChIKey", "Indigo InChIKey");
    }


    public List<String> getErrorMsgs(String notFoundWord){
        List<String> errors = new ArrayList<>();

        if(isCasrn(notFoundWord)){
            errors.add("Searched by CASRN: Found 0 results for '" + notFoundWord + "'.");
            if(!checkCasrnFormat(notFoundWord, true))
                errors.add("CAS number fails checksum.");
        } else if(isDtxcid(notFoundWord)){
            errors.add("Searched by DTX Compound Id: Found 0 results for '" + notFoundWord + "'.");
        } else if(isDtxsid(notFoundWord)){
            errors.add("Searched by DTX Substance Id: Found 0 results for '" + notFoundWord + "'.");
        } else if(isInchiKey(notFoundWord)){
            errors.add("Searched by InChI Key: Found 0 results for '" + notFoundWord + "'.");
        } else if(isInchiKeySkeleton(notFoundWord)) {
            errors.add("Searched by InChI String: Found 0 results for '" + notFoundWord + "'.");
        } else if(isCasrn(notFoundWord)) {
            errors.add("Searched by CASRN: Found 0 results for '" + notFoundWord + "'.");
            if (!checkCasrnChecksum(notFoundWord))
                errors.add("CAS number fails checksum.");
        }else{
            errors.add("Searched by Synonym: Found 0 results for '" + notFoundWord + "'.");
        }

        return errors;
    }

    public String[] preprocessingSearchWord(String[] searchWords){

        List<String> searches = new ArrayList<>();

        for(String word : searchWords){
            searches.add(preprocessingSearchWord(word));
        }

        return searches.toArray(new String[0]);
    }

    public String preprocessingSearchWord(String searchWord){

        // From https://confluence.epa.gov/display/CCTEA/Search+Requirements
        // Make all character upper case
        searchWord = searchWord.toUpperCase();
        // Search word should be trim
        searchWord = searchWord.trim();

        // Checking if it's an EC number first, to avoid removing leading zeros from EC numbers
        if (isECNumber(searchWord)) {
            log.debug("{} is EC number", searchWord);
            return searchWord;
        }

        // Checking if it's a CASRN-like format with or without dashes and removing leading zeros
        if (searchWord.matches("^0*\\d{1,7}-?\\d{2}-?\\d$")) {
            searchWord = searchWord.replaceFirst("^0+", "");
        }

        if (isCasrn(searchWord)) {
            log.debug("{} is casrn", searchWord);
            return searchWord;
        } else if (isInchiKey(searchWord)) {
            log.debug("{} is inchiKey", searchWord);
            return searchWord;
        } else if (isCasrn(processCasrnDashes(searchWord))) {
            log.debug("{} is casrn with wrong dash", searchWord);
            return processCasrnDashes(searchWord);
        } else if (StringUtils.isNumeric(searchWord.replaceAll("-", ""))) {
            // Partial CASRN or EC Number
            return searchWord;
        } else {

            // check for URLencoded value for Synonyms
//            if(isUrlEncoded(searchWord)){
//                try {
//                    log.info("searchWord is URL encoded: {}", searchWord);
//                    searchWord = URLDecoder.decode(searchWord, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            // For allowing search JW-001 and JW 001
            searchWord = searchWord.replaceAll("-"," ");
            return searchWord;
        }
    }

    // This is for replacing different types of dashes with -
    private static String processCasrnDashes(String searchWord) {
         return StringUtils.replaceChars(searchWord,"‐–—‒―−","------");

        // return searchWord.replaceAll("–", "-");
    }

    private boolean isUrlEncoded(String input) {
        return ENCODED_PATTERN.matcher(input).find();
    }

    public boolean isDtxcid(String dtxcid) {
        dtxcid = dtxcid.toUpperCase();
        return dtxcid.matches("DTXCID(.*)");
    }

    public boolean isDtxsid(String dtxsid) {
        dtxsid = dtxsid.toUpperCase();
        return dtxsid.matches("DTXSID(.*)");
    }


    public boolean isCasrn(String casrn) {
        return casrn.matches("^\\d{1,7}-\\d{2}-\\d$");
    }

    public boolean isECNumber(String casrn) {
        return casrn.matches("^\\d{3}-\\d{3}-\\d$");
    }

    public boolean isInchiKey(String inchikey) {
        inchikey = inchikey.toUpperCase();
        return inchikey.matches("[A-Z]{14}-[A-Z]{10}-[A-Z]");
    }

    public boolean isInchiKeySkeleton(String inchikeyskeleton) {
        inchikeyskeleton = inchikeyskeleton.toUpperCase();
        return inchikeyskeleton.matches("[A-Z]{14}");
    }

    public boolean isChemicalSynonym(String word){

        return !isCasrn(word) && !isDtxcid(word) && !isDtxsid(word) && !isECNumber(word) && !isInchiKey(word) && !isInchiKeySkeleton(word);
    }

    //  this will not check -ve and decimal numbers
    private Boolean isNumeric(String number) {
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;

//        return  number.chars().allMatch( Character::isDigit );
    }

    public String number2Casrn(Integer number) {
        String temp = number.toString();
        return String.format("%s-%s-%s", temp.substring(0, temp.length() - 3), temp.substring(temp.length() - 3, temp.length() - 1), temp.substring(temp.length() - 1));
    }
    //
    public String toCasrn(String word){
        // 75-O5-8 Letter O instead of 0
        word = word.replaceAll("O", "0");
        // 80/05/7 -- batchsearch has some advance case, I am right now only implementing the simple case
        word = word.replaceAll("/", "");

        // Now remoave all - to make it a number
        word = word.replaceAll("/", "");

        if(isNumeric(word)){
            // remove leading zeros
            return number2Casrn(Integer.valueOf(word));
        }else
            return null;

    }

    public boolean checkCasrnChecksum(String casrn) {
// Check the string against the mask
        if (!casrn.matches("^\\d{1,7}-\\d{2}-\\d$")) {
            return false;
        } else {
// Remove the dashes
            casrn = casrn.replaceAll("-", "");
            int sum = 0;
            for (int indx = 0; indx < casrn.length() - 1; indx++) {
                sum += (casrn.length() - indx - 1) * Integer.parseInt(casrn.substring(indx, indx + 1));
            }
// Check digit is the last char, compare to sum mod 10.
            log.debug("v1= {} and v2= {}",Integer.parseInt(casrn.substring(casrn.length() - 1)), (sum % 10));
            return Integer.parseInt(casrn.substring(casrn.length() - 1)) == (sum % 10);
        }
    }

    // This will remove duplicates(same dtxsid number) from search result
    public List removeDuplicates(List chemicals) {

        List returnList = new ArrayList<>();
        List<String> dtxsidList = new ArrayList<>();
        int duplicateCount = 0;
        //List<String> dtxcidList = new ArrayList<String>();

        for(Object chemical : chemicals){
            //if(chemical.getDtxsid() != null ){}
            String dtxsid = "";
            if(chemical instanceof ChemicalSearchAll)
                dtxsid = ((ChemicalSearchAll) chemical).getDtxsid();
            else if (chemical instanceof CcdChemicalSearchResult)
                dtxsid = ((CcdChemicalSearchResult) chemical).getDtxsid();
            else if (chemical instanceof DtxsidOnly)
                dtxsid = ((DtxsidOnly) chemical).getDtxsid();
//            else if (chemical instanceof )
//                dtxsid = ((DtxsidOnly) chemical).getDtxsid();
            if(!dtxsidList.contains(dtxsid)){
                dtxsidList.add(dtxsid);
                returnList.add(chemical);
            } else{
                duplicateCount++;
            }
        }
        log.debug("{} are skipped as duplicate", duplicateCount);
        return returnList;
    }

//    public List<DtxsidOnly> removeDuplicates(List<DtxsidOnly> chemicals){
//        List<DtxsidOnly> returnList = new ArrayList<>();
//        List<String> dtxsidList = new ArrayList<>();
//        int duplicateCount = 0;
//
//        for(DtxsidOnly chemical : chemicals){
//            if(dtxsidList.contains(chemical.getDtxsid()) == false){
//                dtxsidList.add(chemical.getDtxsid());
//                returnList.add(chemical);
//            }else{
//                duplicateCount++;
//            }
//        }
//
//        log.debug("{} are skipped as duplicate", duplicateCount);
//        return returnList;
//    }

    public List<String> getSuggestions(String word) {

        // we will get caffeinefix suggestion for synonyms,
        // caffeinefix data dictionary is case-sensitive, this is why we are converting word to lower case.
        //         if(!isCasrn(word) && !isDtxcid(word) && !isDtxsid(word) && !isECNumber(word) && !isInchiKey(word) && !isInchiKeySkeleton(word)){
        List<String> suggestions = null;

        if(!isNumeric(word) && !isCasrn(word)) // 80057 - 000008057 (CASRN without dashes)
            suggestions = caffeineFixService.caffeineFix(word.toLowerCase());

        if(suggestions != null && !suggestions.isEmpty()){
            return suggestions;
        }else{
            if(isInchiKey(word))
                suggestions = inchikeySuggestion(word);
            else if(isCasrn(word))
                suggestions = new ArrayList<>(Collections.singletonList(toCasrn(word)));
            else
                // if search is systematic name, find the inchikey for it
                suggestions = opsinSuggestion(word);
        }

        return suggestions;
    }

    private List<String> opsinSuggestion(String word) {
        log.debug("checking is search word = {} is systematic name", word);

        String inchikey = opsinService.toInChIKey(word);

        return Collections.singletonList(inchikey);
    }

    private List<String> inchikeySuggestion(String word) {

        List<String> suggestions = searchRepository.getInchiKey(inchikeyWithoutCharge(word));

        if(!suggestions.isEmpty()){
            return suggestions;
        }else{
            return searchRepository.getInchiKey(inchikeyWithoutSecondlayer(word));
        }
    }

    public List<ChemicalBatchSearchResult> processBatchResult(List<ChemicalSearchInternal> searchResult, String[] searchWords) {
        // create a hashmap with searchResult where use searchValue as key
        // this will help us to find the searchResult for a given searchWord
        HashMap<String, ChemicalSearchInternal> searchResultMap = new HashMap<>();

        // create hasmap for dtxsid to check duplicates
        HashMap<String, String> dtxsidMap = new HashMap<>();

        for(ChemicalSearchInternal result : searchResult){
            searchResultMap.put(result.getModifiedValue(), result);
        }

        //log.debug("searchResultMap size = {}, keys = {}", searchResultMap.size(), searchResultMap.keySet());


        // create a new list of ChemicalBatchSearchResult to return
        List<ChemicalBatchSearchResult> returnList = new ArrayList<>();

        // I need to loop through searchWords, if they are in searchResultMap, I will add them to returnList
        for(String searchWord : searchWords){
            String processedSearchWord = preprocessingSearchWord(searchWord);
            if(searchResultMap.containsKey(processedSearchWord)){
                ChemicalSearchInternal result = searchResultMap.get(processedSearchWord);
                returnList.add(new ChemicalBatchSearchResult(result.getDtxsid(), result.getDtxcid(), result.getCasrn(), result.getSmiles(), result.getPreferredName(), result.getSearchName(), searchWord, result.getRank(), result.getHasStructureImage(), result.getIsMarkush(), null, null, dtxsidMap.containsKey(result.getDtxsid())));
                dtxsidMap.put(result.getDtxsid(), result.getDtxsid());
            }else{
                returnList.add(new ChemicalBatchSearchResult(null, null, null, null, null, null, searchWord, null, null, null, getErrorMsgs(searchWord), getSuggestions(searchWord),false));
            }
        }

        return returnList;
    }


    public HashMap<Double, List<String>> getMsReadyBatchResult(BatchMsReadyMassForm form){

        HashMap<Double, List<String>> result = new HashMap<>();

        for(Double mass: form.getMasses()){
            Double error = mass * form.getError() / 1000000;
            Double start = mass - error;
            Double end = mass + error;
            log.debug("mass={} error={} cal-error={} start={} end={}",mass, form.getError(),error, start, end);
            List<String> dtxsids = searchRepository.searchMsReadyMass(start,end);
            result.put(mass, dtxsids);
        }

        return result;
    }

    public List<ChemicalSearchAll> getStartWith(String word, Integer top) {
        String searchWord = preprocessingSearchWord(word);


        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearchAll> searchResult; // exact search and final results
        List<ChemicalSearchAll> searchResult2; // start-with results

        // for adding exact search on top of return result
        String removeSpaces = searchWord.replaceAll(" ", "");

        // searchResult = searchRepository.findByModifiedValueInOrderByRankAsc(List.of(searchWord, removeSpaces),ChemicalSearchAll.class);
        searchResult = searchRepository.findByModifiedValueInAndSearchNameInOrderByRankAsc(List.of(searchWord, removeSpaces), searchNames4SingleSearch, ChemicalSearchAll.class);

        log.debug("exact search count {}",searchResult.size());

        if(shouldSearchMore(searchWord, searchResult)) {
            // avoid InChIKey
            if (searchWord.length() > 13) {
                log.debug("search with inchikey");
                searchResult2 = getStartWithFromDB(searchWord, searchMatchWithInchikey, top);
            } else {
                log.debug("search without inchikey");
                searchResult2 = getStartWithFromDB(searchWord, searchMatchWithoutInchikey, top);
            }

            searchResult.addAll(searchResult2); // append start-with results
        }
        log.debug("{} records match for {}", searchResult.size(), word);

        searchResult = removeDuplicates(searchResult);
        return searchResult;
    }

    private List<ChemicalSearchAll> getStartWithFromDB(String searchWord, List<String> searchMatchValues, Integer top) {

        if(top != null && top > 0 ){
            log.debug("picking up top {} records", top);
            return searchRepository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(searchWord,
                    searchMatchValues, Limit.of(top),
                    ChemicalSearchAll.class);
        }else{
            return searchRepository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(searchWord,
                    searchMatchValues, Limit.unlimited(),
                    ChemicalSearchAll.class);
        }
    }


    // identify the condition if there is not more searching needed
    private boolean shouldSearchMore(String searchWord, List<ChemicalSearchAll> searchResult) {
        if(ChemicalUtils.isDtxsid(searchWord) || ChemicalUtils.isDtxcid(searchWord) || ChemicalUtils.isCasrn(searchWord))
            return false;
            // in case CASRN is in number format
        else if (!searchResult.isEmpty() && isThisCASRN.contains(searchResult.get(0).getSearchName()))
            return false;
        else
            return true;
    }

    public List getContain(String projection, String searchWord, Integer top) {
        switch (projection){
            case "chemicalsearchall": {
                 // ChemicalSearchAll.class
                List result = getContainFromDB1(searchWord, top, ChemicalSearchAll.class);
                return removeDuplicates(result);
            }
            case "dtxsidonly":{
                // DtxsidOnly.class
                List result =  getContainFromDB1(searchWord, top, DtxsidOnly.class);
                return removeDuplicates(result);
            }
            case "ccdsearchresult":{
                if(top != null && top > 0 ) {
                    List result = searchRepository.containCcd(searchWord);
                    return result.stream().limit(top).toList();
                }else{
                    return searchRepository.containCcd(searchWord);
                }
            }
            default: return null;
        }
    }

//    private List getContainFromDB1(String searchWord, Integer top, Class aClass) {
//        if(top != null && top > 0 ) {
//            log.debug("picking up top {} records", top);
//            return searchRepository.findByModifiedValueContainsAndSearchNameInOrderByRankAscDtxsidAsc(searchWord, searchMatchWithoutInchikey, Limit.of(top), aClass);
//            //return searchRepository.findByModifiedValueContainsOrderByRankAscDtxsid(searchWord,Limit.of(top), aClass);
//        }else{
//            return searchRepository.findByModifiedValueContainsAndSearchNameInOrderByRankAscDtxsidAsc(searchWord, searchMatchWithInchikey, Limit.unlimited(), aClass);
//            //return searchRepository.findByModifiedValueContainsOrderByRankAscDtxsid(searchWord, Limit.unlimited(), aClass);
//        }
//    }

    private List getContainFromDB1(String searchWord, Integer top, Class aClass) {
        // Determine if Inchikey logic should be included based on searchWord length
        boolean useInchikey = searchWord != null && searchWord.length() >= 13;

        // Determine the appropriate search match type
        var searchMatch = useInchikey ? searchMatchWithInchikey : searchMatchWithoutInchikey;

        // Determine the limit based on the 'top' parameter
        var limit = (top != null && top > 0) ? Limit.of(top) : Limit.unlimited();

        // Call the repository method with the appropriate parameters
        return searchRepository.findByModifiedValueContainsAndSearchNameInOrderByRankAscDtxsidAsc(
                searchWord,
                searchMatch,
                limit,
                aClass
        );
    }


    // This will remove duplicates(same dtxsid number) from search result
/*    public List<SearchResult> removeSearchResultDuplicates(List<SearchResult> results) {

        List<SearchResult> returnList = new ArrayList<SearchResult>();
        List<String> dtxsidList = new ArrayList<String>();

        for(SearchResult result : results){
            if(dtxsidList.contains(result.getDtxsid()) == false){
                dtxsidList.add(result.getDtxsid());
                returnList.add(result);
            } else{
                log.debug("skip duplicate -  " + result );
            }
        }
        return returnList;
    }*/
    
}


