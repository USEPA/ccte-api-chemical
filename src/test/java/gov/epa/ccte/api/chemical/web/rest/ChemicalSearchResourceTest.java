package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ChemicalSearchResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Limit;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.projection.search.*;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.service.SearchFormulaService;

import java.math.BigInteger;
import java.util.*;

@ActiveProfiles("test")
@WebMvcTest(ChemicalSearchResource.class)
@RunWith(MockitoJUnitRunner.class)
public class ChemicalSearchResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalSearchRepository searchRepository;
    @MockitoBean 
    private SearchChemicalService searchService;
    
    @MockitoBean 
    private SearchFormulaService searchFormulaService;
    
    private BatchMsReadyMassForm form;
    private ChemicalBatchSearchResult batchResult;
    private CcdChemicalSearchResult ccdResult;
    private ChemicalSearchAll allResult;
    private ChemicalSearchInternal internalResult;
    private DtxsidOnly dtxsidResult;
    
    private ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    @BeforeEach
	public void setup() {
    	
    	allResult = projectionFactory.createProjection(ChemicalSearchAll.class);
    	allResult.setDtxsid("DTXSID2021001");
    	allResult.setDtxcid("DTXCID2021001");
    	allResult.setCasrn("50-00-0");
    	allResult.setSmiles("C=O");
    	allResult.setPreferredName("Formaldehyde");
    	allResult.setSearchName("Formaldehyde");
    	allResult.setSearchValue("formaldehyde");
    	allResult.setRank(1);
    	allResult.setHasStructureImage(1);
    	allResult.setIsMarkush(false);
    	
    	// Retrieves modified_value from database
    	internalResult = projectionFactory.createProjection(ChemicalSearchInternal.class);
    	internalResult.setId(1);
    	internalResult.setDtxsid("DTXSID2021001");
    	internalResult.setDtxcid("DTXCID2021001");
    	internalResult.setCasrn("50-00-0");
    	internalResult.setSmiles("C=O");
    	internalResult.setPreferredName("Formaldehyde");
    	internalResult.setSearchGroup("name");
    	internalResult.setSearchName("Formaldehyde");
    	internalResult.setSearchValue("formaldehyde");
    	internalResult.setModifiedValue("formaldehyde");
    	internalResult.setRank(1);
    	internalResult.setHasStructureImage(1);
    	internalResult.setIsMarkush(false);
    	internalResult.setCreatedBy("test");
    	internalResult.setCreatedAt(java.time.Instant.now());
    	
    	dtxsidResult = projectionFactory.createProjection(DtxsidOnly.class);
    	dtxsidResult.setDtxsid("DTXSID2021001");
    	
    	ccdResult = CcdChemicalSearchResult.builder()
    		.dtxsid("DTXSID9020112")
    		.dtxcid("DTXCID90112")
    		.genericSubstanceId(20112)
    		.casrn("1912-24-9")
    		.preferredName("Atrazine")
    		.compoundId(112)
    		.stereo(0)
    		.isotope(0)
    		.multicomponent(0)
    		.pubchemCount(191)
    		.pubmedCount(14356)
    		.sourcesCount(170)
    		.cpdataCount(30L)
    		.activeAssays(57)
    		.totalAssays(761)
    		.percentAssays(new BigInteger("7"))
    		.toxcastSelect("57/761")
    		.monoisotopicMass(215.0937732)
    		.molFormula("C8H14ClN5")
    		.qcLevel(1)
    		.qcLevelDesc("Level 1: Expert curated, highest confidence in accuracy and consistency of unique chemical identifiers")
    		.pubchemCid(2256)
    		.hasStructureImage(true)
    		.relatedSubstanceCount(27)
    		.relatedStructureCount(16)
    		.iupacName("6-Chloro-N~2~-ethyl-N~4~-(propan-2-yl)-1,3,5-triazine-2,4-diamine")
    		.smiles("CCNC1=NC(NC(C)C)=NC(Cl)=N1")
    		.inchiString("InChI=1S/C8H14ClN5/c1-4-10-7-12-6(9)13-8(14-7)11-5(2)3/h5H,4H2,1-3H3,(H2,10,11,12,13,14)\n")
    		.inchikey("MXWJVTOOROXGIU-UHFFFAOYSA-N")
    		.averageMass(215.69)
    		.rank(0)
    		.searchMatch("Approved Name")
    		.searchWord("Atrazine")
    		.build();
    	
    	batchResult = ChemicalBatchSearchResult.builder()
			.dtxsid("DTXSID7020637")
			.dtxcid("DTXCID30637")
			.casrn("50-00-0")
			.smiles("C=O")
			.preferredName("Formaldehyde")
			.searchName("Systematic Name")
			.searchValue("FORMALDEHYDE")
			.rank(12)
			.hasStructureImage(1)
			.isMarkush(false)
			.searchMsgs(null)
			.suggestions(null)
			.isDuplicate(false)
			.build();
    	
    	form = new BatchMsReadyMassForm();
		form.setMasses(new Double[] {12.0,16.1});
		form.setError(2);
    }
    
    @Test
    void testChemicalStartWith() throws Exception {
		final List<ChemicalSearchAll> resultList = Collections.singletonList(allResult);
		
		when(searchService.getStartWith(eq("form"), any())).thenReturn(resultList);
		
		mockMvc.perform(get("/chemical/search/start-with/{chem-name}", "form"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].preferredName").value(allResult.getPreferredName()));
    }
    
    @Test
    void testChemicalEqual() throws Exception {
		final List<ChemicalSearchAll> resultList = Collections.singletonList(allResult);
        final List<?> searchResult = searchRepository.findByModifiedValueOrderByRankAsc("formaldehyde", ChemicalSearchAll.class);
        
		when(searchService.removeDuplicates(searchResult)).thenReturn(resultList);
		when(searchService.applyStartWithRankFilter(resultList)).thenReturn(resultList);

		
		mockMvc.perform(get("/chemical/search/equal/{chem-name}", "formaldehyde"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
    void testChemicalEqualProjection() throws Exception {
		final List<ChemicalSearchAll> resultList = Collections.singletonList(allResult);
        final List<?> searchResult = searchRepository.findByModifiedValueOrderByRankAsc("formaldehyde", ChemicalSearchAll.class);
        
		when(searchService.removeDuplicates(searchResult)).thenReturn(resultList);
		when(searchService.applyStartWithRankFilter(resultList)).thenReturn(resultList);


		
		
		mockMvc.perform(get("/chemical/search/equal/{chem-name}", "formaldehyde")
				.param("projection", "chemicalsearchall"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
    void testChemicalEqualDtxsidOnly() throws Exception {
		final List<DtxsidOnly> resultList = Collections.singletonList(dtxsidResult);
        final List<?> searchResult = searchRepository.findByModifiedValueOrderByRankAsc("formaldehyde", DtxsidOnly.class);
        
		when(searchService.removeDuplicates(searchResult)).thenReturn(resultList);
		
		mockMvc.perform(get("/chemical/search/equal/{chem-name}", "formaldehyde")
				.param("projection", "dtxsidonly"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dtxsid").value(allResult.getDtxsid()));
    }
    
    @Test
    void testChemicalEqualCcd() throws Exception {
		final List<CcdChemicalSearchResult> resultList = Collections.singletonList(ccdResult);
        final List<CcdChemicalSearchResult> searchResult = searchRepository.equalCcd("Atrazine");
        
		when(searchService.removeDuplicates(searchResult)).thenReturn(resultList);
		when(searchService.applyRankFilterSearchResult(resultList)).thenReturn(resultList);

		
		mockMvc.perform(get("/chemical/search/equal/{chem-name}", "Atrazine")
				.param("projection", "ccdsearchresult"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
    void testChemicalContain() throws Exception {
        final List<ChemicalSearchAll> finalResult = Collections.singletonList(allResult);
    	final String processedWord = "MALDEHYDE";
    	final List<String> searchMatchWithoutInchikey = Arrays.asList("Approved Name", "Systematic Name", "Synonym", "Trade Name", "Other Name");
    	final List<?> searchResult = searchRepository.findByModifiedValueContainsAndSearchNameInOrderByRankAscDtxsidAsc(processedWord,searchMatchWithoutInchikey,Limit.of(1),ChemicalSearchAll.class);
    	
    	when(searchService.preprocessingSearchWord("maldehyde")).thenReturn(processedWord);
    	
    	when(searchService.getContain(eq("chemicalsearchall"), eq(processedWord), eq(1))).thenReturn(searchResult);

    	when(searchService.removeDuplicates(searchResult)).thenReturn(finalResult);

        mockMvc.perform(get("/chemical/search/contain/{word}", "maldehyde")
                .param("top", "1")
                .param("projection", "chemicalsearchall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(allResult.getPreferredName())));
    }
    
    @Test
    void testChemicalBatchEqual() throws Exception {
		final List<ChemicalBatchSearchResult> resultList = Collections.singletonList(batchResult);
		final String searchWords = "formaldehyde/nacetaldehyde";
		
        when(searchService.preprocessingSearchWord(any(String[].class))).thenReturn(new String[] {"formaldehyde", "nacetaldehyde"});
        
		final String[] processedWords = searchService.preprocessingSearchWord(searchWords.split("\n"));
		final List<ChemicalSearchInternal> searchResult = searchRepository.findByModifiedValueInOrderByRankAsc(List.of(processedWords), ChemicalSearchInternal.class);
		
		when(searchService.processBatchResult(searchResult, processedWords, processedWords)).thenReturn(resultList);
		
		mockMvc.perform(post("/chemical/search/equal/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(searchWords))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}
    
    @Test
    void testMsReadyByFormula() throws Exception {
    	List<String> dtxsids = Arrays.asList("DTXSID00874844", "DTXSID201151393");
    	
		when(searchRepository.searchMsReadyFormula("C16H24N2O5S")).thenReturn(dtxsids);
		
		mockMvc.perform(get("/chemical/msready/search/by-formula/{mol-formula}", "C16H24N2O5S"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("DTXSID00874844")));
    }
    
    @Test
    void testMsReadyByDtxcid() throws Exception {
    	List<String> dtxsids = Arrays.asList("DTXSID00874844", "DTXSID201151393");
    	
		when(searchRepository.searchMsReadyDtxcid("DTXCID30182")).thenReturn(dtxsids);
		
		mockMvc.perform(get("/chemical/msready/search/by-dtxcid/{dtxcid}", "DTXCID30182"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("DTXSID00874844")));
    }
    
    @Test
    void testMsReadyByBatchDtxcid() throws Exception {
        List<String> dtxsids = Arrays.asList("DTXSID00874844", "DTXSID201151393");
        String[] dtxcidArray = {"DTXCID30182","DTXCID90112"};
        String jsonBody = new ObjectMapper().writeValueAsString(dtxcidArray);

        when(searchRepository.searchMsReadyByBatchDtxcid(dtxcidArray)).thenReturn(dtxsids);

        mockMvc.perform(post("/chemical/msready/search/by-dtxcid/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DTXSID00874844")))
                .andReturn();
    }
    
    @Test
    void testMsReadyByMass() throws Exception {
    	List<String> dtxsids = Arrays.asList("DTXSID00874844", "DTXSID201151393");
    	
		when(searchRepository.searchMsReadyMass(154.9, 154.95)).thenReturn(dtxsids);
		
		mockMvc.perform(get("/chemical/msready/search/by-mass/154.9/154.95"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("DTXSID00874844")));
    }
    
    @Test
    void testMsReadyByBatchMass() throws Exception {
        HashMap<Double, List<String>> dtxsids = new HashMap<>();
        dtxsids.put(12.0, Arrays.asList("DTXSID10846370","DTXSID90166624" ,"DTXSID9027651"));
        dtxsids.put(16.1, Arrays.asList());
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("error", 2);
        jsonMap.put("masses", new Double[]{12.0, 16.1});
        String jsonBody = new ObjectMapper().writeValueAsString(jsonMap);

        when(searchService.getMsReadyBatchResult(any(BatchMsReadyMassForm.class))).thenReturn(dtxsids);

        mockMvc.perform(post("/chemical/msready/search/by-mass/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("DTXSID10846370")))
            .andReturn();
    }
}

