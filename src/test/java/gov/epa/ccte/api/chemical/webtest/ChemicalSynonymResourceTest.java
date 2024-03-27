package gov.epa.ccte.api.chemical.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

// chemical/synonym/search/by-dtxsid/{dtxsid}
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChemicalSynonymResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    // Test for chemical/synonym/search/by-dtxsid/{dtxsid}
    @Test
    void testChemicalSynonymSearchByDtxsidForPositiveResponse() {
        webTestClient.get().uri("chemical/synonym/search/by-dtxsid/DTXSID7020182").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

}
