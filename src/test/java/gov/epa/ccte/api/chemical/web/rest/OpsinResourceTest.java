package gov.epa.ccte.api.chemical.web.rest;

//This will test REST end-points in the OpsinResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.service.OpsinService;

@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.WARN)
@WebMvcTest(OpsinResource.class)
@ExtendWith(MockitoExtension.class)
public class OpsinResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OpsinService opsinService;

    @Test
    void testToInchi() throws Exception {

        when(opsinService.toInChI("acetamide")).thenReturn("InChI=1/C2H5NO/c1-2(3)4/h1H3,(H2,3,4)/f/h3H2");

        mockMvc.perform(get("/chemical/opsin/to-inchi/acetamide"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("InChI=1/C2H5NO/c1-2(3)4/h1H3,(H2,3,4)/f/h3H2"));
    }

    @Test
    void testToInchikey() throws Exception {

        when(opsinService.toInChIKey("acetamide")).thenReturn("DLFVBJFMPXGRIB-UHFFFAOYSA-N");

        mockMvc.perform(get("/chemical/opsin/to-inchikey/acetamide"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("DLFVBJFMPXGRIB-UHFFFAOYSA-N"));
    }

    @Test
    void testToSmiles() throws Exception {

        when(opsinService.toSmiles("acetamide")).thenReturn("C(C)(=O)N");

        mockMvc.perform(get("/chemical/opsin/to-smiles/acetamide"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("C(C)(=O)N"));
    }

}
