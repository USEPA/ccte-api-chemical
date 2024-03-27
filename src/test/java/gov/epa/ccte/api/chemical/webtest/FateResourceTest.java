package gov.epa.ccte.api.chemical.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FateResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    // chemical/fate/search/by-dtxsid/{dtxsid}
    @Test
    void testChemicalFateSearchByDtxsidForPositiveResponse() {
        webTestClient.get().uri("chemical/fate/search/by-dtxsid/DTXSID7020182").exchange()
                .expectStatus().isOk();
    }
}
