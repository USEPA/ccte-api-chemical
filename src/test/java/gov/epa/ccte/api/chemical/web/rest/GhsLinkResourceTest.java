package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the GhsLinkResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;

import java.util.*;

@ActiveProfiles("test")
@WebMvcTest(GhsLinkResource.class)
@RunWith(MockitoJUnitRunner.class)
public class GhsLinkResourceTest {

	@Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalListChemicalRepository repository;
    
    private GhsLinkResponse ghsResponse;
    
    @BeforeEach
    void setUp(){
    	
    	ghsResponse = GhsLinkResponse.builder()
				.dtxsid("DTXSID7020182")
				.isSafetyData(true)
				.safetyUrl("https://pubchem.ncbi.nlm.nih.gov/compound/DTXSID7020182#section=GHS-Classification")
				.build();
    }
    
    @Test
    void testByDtxsid() throws Exception {
		final List<GhsLinkResponse> ghs = Collections.singletonList(ghsResponse);
		
    	when(repository.isGhsLinkExists(any(String[].class))).thenReturn(ghs);
		
		mockMvc.perform(get("/chemical/ghslink/to-dtxsid/{dtxsid}", "DTXSID7020182"))
				.andDo(MockMvcResultHandlers.print())				
				.andExpect(status().isOk())				
				.andExpect(content().string(containsString("DTXSID7020182")));
	}
    
    @Test
    void testByBatchDtxsid() throws Exception {
    	final List<GhsLinkResponse> ghs = Collections.singletonList(ghsResponse);
        String dtxsid = "DTXSID7020182";
        String[] jsonArray = new String[]{dtxsid};
        String jsonBody = new ObjectMapper().writeValueAsString(jsonArray);
        
		when(repository.isGhsLinkExists(jsonArray)).thenReturn(ghs);
		
		mockMvc.perform(post("/chemical/ghslink/to-dtxsid/")
    			.accept(MediaType.APPLICATION_JSON_VALUE)
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(jsonBody))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dtxsid").value(ghsResponse.getDtxsid()));
		
    }
}
