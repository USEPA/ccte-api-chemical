package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ExtraDataResource.java using WebMvcTest and MockitoBean

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.domain.ExtraData;
import gov.epa.ccte.api.chemical.repository.ExtraDataRepository;

import java.util.*;

@ActiveProfiles("test")
@WebMvcTest(ExtraDataResource.class)
@RunWith(MockitoJUnitRunner.class)
public class ExtraDataResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ExtraDataRepository repository;
    
    private ExtraData extraData;
    
    @BeforeEach
	void setUp(){
    	
    	extraData = ExtraData.builder()
    			.dtxsid("DTXSID7020182")
    			.dtxcid(6623)
    			.refs(112)
    			.googlePatent(253889)
    			.literature(22083)
    			.pubmed(8825)
    			.build();
    }
    
    @Test
    void testByDtxsid() throws Exception {
		final List<ExtraData> data = Collections.singletonList(extraData);
		
    	when(repository.findByDtxsid("DTXSID7020182", ExtraData.class)).thenReturn(data);
		
		mockMvc.perform(get("/chemical/extra-data/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
				.andDo(MockMvcResultHandlers.print())				
				.andExpect(status().isOk())				
				.andExpect(jsonPath("$[0].dtxsid").value(extraData.getDtxsid()));
	}
    
    @Test
    void testByBatchDtxsid() throws Exception {
    	final List<ExtraData> data = Collections.singletonList(extraData);
        String dtxsid = "DTXSID7020182";
        String[] jsonArray = new String[]{dtxsid};
        String jsonBody = new ObjectMapper().writeValueAsString(jsonArray);
        
		when(repository.findByDtxsidInOrderByDtxsidAsc(jsonArray, ExtraData.class)).thenReturn(data);
		
		mockMvc.perform(post("/chemical/extra-data/search/by-dtxsid/")
    			.accept(MediaType.APPLICATION_JSON_VALUE)
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(jsonBody))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dtxsid").value(extraData.getDtxsid()));
		
    }

}
