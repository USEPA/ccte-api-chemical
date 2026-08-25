package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the GhsLinkResource.java using WebMvcTest and MockitoBean

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
@WebMvcTest(GhsLinkResource.class)
@ExtendWith(MockitoExtension.class)
public class GhsLinkResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalListChemicalRepository repository;

    private GhsLinkResponse ghsResponse;

    @BeforeEach
    void setUp() {

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
        String jsonBody = new JsonMapper().writeValueAsString(jsonArray);

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
