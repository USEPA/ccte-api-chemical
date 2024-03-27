package gov.epa.ccte.api.chemical.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to Chemical.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {

    private String apiKeyName;
    private String apiEnv;
    private String apiUrl;
    private Integer batchSize;

//    private String docHostReplacement;
//    private int docHostPort;
//    private String docHostHttpProtocol;
//    private Integer startPort;
//    private Integer endPort;
//    private Integer serverPort;
    private final CorsConfiguration cors = new CorsConfiguration();
}
