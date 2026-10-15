package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@WebMvcTest(ChemicalResource.class)
@RunWith(MockitoJUnitRunner.class)
public class ChemicalResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

    /**
     * Test health endpoint returns 200 OK when database connection is successful.
     */
    @Test
    void testHealthCheckSuccess() throws Exception {
        doNothing().when(jdbcTemplate).execute("SELECT 1 ");

        mockMvc.perform(get("/chemical/health"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    /**
     * Test health endpoint returns 404 NOT_FOUND when DataAccessException is thrown.
     */
    @Test
    void testHealthCheckDatabaseAccessException() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(jdbcTemplate).execute("SELECT 1 ");

        mockMvc.perform(get("/chemical/health"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }


}
