package gov.epa.ccte.api.chemical.webtest;

// This will test REST endpoints using webtestclient define in chemicaldetailresource.java

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MimeType;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.asMediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChemicalDetailResourceTest {
    @Autowired
    private WebTestClient webTestClient;

    // Test the endpoint for getting chemical detail by dtxsid
    @Test
    void testChemicalDetailByDtxsidForPositiveResponse() {
        webTestClient.get().uri("chemical/detail/search/by-dtxsid/DTXSID7020182").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);

    }
    // chemical/detail/search/by-dtxsid/DTXSID7020182?projection=ntatoolkit

    @Test
    void testChemicalDetailByDtxsidWithProjection() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path("chemical/detail/search/by-dtxsid/DTXSID7020182")
                        .queryParam("projection", "ntatoolkit")
                        .build()).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);

    }

    @Test
    void testChemicalDetailByDtxsidForNegativeResponse() {
        webTestClient.get().uri("chemical/detail/search/by-dtxsid/DTXSID7020xx").exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(asMediaType(MimeType.valueOf("application/problem+json")));

    }

    @Test
    void testChemicalDetailByDtxsidBatchForPositiveResponse() {
        webTestClient.post().uri("chemical/detail/search/by-dtxsid/").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);

    }


    // Test the endpoint for getting chemical detail by dtxcid
    @Test
    void testChemicalDetailByDtxcidForPositiveResponse() {
        webTestClient.get().uri("chemical/detail/search/by-dtxcid/DTXCID30182").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);

    }
}
