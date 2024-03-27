package gov.epa.ccte.api.chemical.webtest;

// This will test REST endpoints using webtestclient define in chemicalListResource.java following are the endpoints
// chemical/list/
// chemical/list/type
// chemical/list/search/by-type/{type}
// chemical/list/search/by-name/{listName}
// chemical/list/search/by-dtxsid/{dtxsid}
// chemical/list/chemicals/search/by-listname/{listName}


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChemicalListResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    // Test for chemical/list/
    @Test
    void testChemicalListForPositiveResponse() {
        webTestClient.get().uri("chemical/list/").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // Test for chemical/list/type
    @Test
    void testChemicalListTypeForPositiveResponse() {
        webTestClient.get().uri("chemical/list/type").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // Test for chemical/list/search/by-type/{type}
    @Test
    void testChemicalListSearchByTypeForPositiveResponse() {
        webTestClient.get().uri("chemical/list/search/by-type/other").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // Test for chemical/list/search/by-dtxsid/{dtxsid}
    @Test
    void testChemicalListSearchByDtxsidForPositiveResponse() {
        webTestClient.get().uri("chemical/list/search/by-dtxsid/DTXSID7020182").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // Test for chemical/list/search/by-name/{listName}
    @Test
    void testChemicalListSearchByNameForPositiveResponse() {
        webTestClient.get().uri("chemical/list/search/by-name/MICROCYSTin").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    // chemical/list/chemicals/search/by-listname/{listName}
    @Test
    void testChemicalListChemicalsSearchByListNameForPositiveResponse() {
        webTestClient.get().uri("chemical/list/chemicals/search/by-listname/MICROCYSTin").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

}
