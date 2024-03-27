package gov.epa.ccte.api.chemical.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

// chemical/property/search/by-dtxsid/{dtxsid}
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChemicalPropertyResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    // Test for chemical/property/search/by-dtxsid/{dtxsid}
    @Test
    void testChemicalPropertySearchByDtxsidForPositiveResponse()
    {
        webTestClient.get().uri("chemical/property/search/by-dtxsid/DTXSID7020182").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // chemical/property/search/by-range/{propertyId}/{start}/{end}
    // "melting-point", 153.0, 157.0
    @Test
    void testChemicalPropertySearchByRangeForPositiveResponse()
    {
        webTestClient.get().uri("chemical/property/search/by-range/melting-point/153.0/157.0").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // chemical/property/experimental/name
    @Test
    void testChemicalPropertyExperimentalNameForPositiveResponse()
    {
        webTestClient.get().uri("chemical/property/experimental/name").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }
}
