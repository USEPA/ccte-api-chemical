package gov.epa.ccte.api.chemical.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MimeType;

import static org.springframework.http.MediaType.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChemicalSearchTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testChemicalStartWithForPositiveResponse() {
        webTestClient.get().uri("chemical/search/start-with/bpa").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);

    }

    @Test
    void testChemicalStartWithForNegativeResponse() {
        webTestClient.get().uri("chemical/search/start-with/benzine").exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(asMediaType(MimeType.valueOf("application/problem+json")))
                .expectBody().consumeWith(System.out::println);
    }

    @Test
    void testChemicalEqualForPositiveResponse() {
        webTestClient.get().uri("chemical/search/equal/bpa").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);

    }

    @Test
    void testChemicalContainForPositiveResponse() {
        webTestClient.get().uri("chemical/search/contain/bpa").exchange()
                .expectStatus().isOk().
                expectHeader().contentType(APPLICATION_JSON);

    }

    @Test
    void testMsReadyBatchMassesSearchForMissingError() {
        webTestClient.post().uri("chemical/msready/search/by-mass/")
                .header(APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
