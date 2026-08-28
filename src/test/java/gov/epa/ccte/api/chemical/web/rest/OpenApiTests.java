package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OpenApiTests {
	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${application.version}")
    private String expectedVersion;

    @Test
    void ensureOpenApiVersionMatchesMavenVersion() {
        String url = "http://localhost:" + port + "/v3/api-docs";
        String jsonResponse = restTemplate.getForObject(url, String.class);

        // Extract version from OpenAPI JSON path info.version
        String apiVersion = JsonPath.read(jsonResponse, "$.info.version");

        assertThat(apiVersion)
                .isNotNull()
                .isEqualTo(expectedVersion);
    }
}
