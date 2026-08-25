package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the WikipediaLinkResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;

import java.util.*;

@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.WARN)
@WebMvcTest(WikipediaLinkResource.class)
@ExtendWith(MockitoExtension.class)
public class WikipediaLinkResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalListChemicalRepository repository;

    private WikipediaLinkResponse wikiResponse;

    @BeforeEach
    void setUp() {

        wikiResponse = WikipediaLinkResponse.builder()
                .dtxsid("DTXSID7020182")
                .safetyUrl("https://en.wikipedia.org/wiki/IISBACLAFKSPIT-UHFFFAOYSA-N#section=wiki-Classification")
                .build();
    }

    @Test
    void testByDtxsid() throws Exception {
        final List<WikipediaLinkResponse> wiki = Collections.singletonList(wikiResponse);

        when(repository.isWikipediaLinkExists(any(String[].class))).thenReturn(wiki);

        mockMvc.perform(get("/chemical/wikipedia/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DTXSID7020182")));
    }

    @Test
    void testByBatchDtxsid() throws Exception {
        final List<WikipediaLinkResponse> wiki = Collections.singletonList(wikiResponse);
        String dtxsid = "DTXSID7020182";
        String[] jsonArray = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(jsonArray);

        when(repository.isWikipediaLinkExists(jsonArray)).thenReturn(wiki);

        mockMvc.perform(post("/chemical/wikipedia/by-dtxsid/")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(wikiResponse.getDtxsid()));

    }

}
