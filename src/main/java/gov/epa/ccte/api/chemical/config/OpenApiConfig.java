package gov.epa.ccte.api.chemical.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "api_key",
        in = SecuritySchemeIn.HEADER,
        description = "Each API request requires an api_key. Contact the CTX API Admin to request an API Key.",
        paramName = "x-api-key"
)
public class OpenApiConfig {
	
	 private final String apiUrl;
	    private final String apiEnvironment;
	    private final BuildProperties buildProperties;

	    public OpenApiConfig(
	            @Value("${application.api-url}") String apiUrl,
	            @Value("${application.api-env}") String apiEnvironment,
	            ObjectProvider<BuildProperties> buildPropertiesProvider
	    ) {
	        this.apiUrl = apiUrl;
	        this.apiEnvironment = apiEnvironment;
	        this.buildProperties = buildPropertiesProvider.getIfAvailable();
	    }
	    
	    @Bean
	    public OpenAPI chemicalOpenApi() {
	        String version = this.buildProperties != null ? this.buildProperties.getVersion() : "unknown";

	        return new OpenAPI()
	                .info(new Info()
	                        .title("Computational Toxicology and Exposure (CTX) APIs - CTX Chemical API")
	                        .description("The CTX Chemical API is part of US EPA's Computational Toxicology and Exposure APIs. The CTX Chemical API provides programmatic access to chemical data through a set of endpoints.")
	                        .contact(new Contact()
	                                .name("")
	                                .url("")
	                                .email(""))
	                        .version(version))
	                .servers(List.of(new Server().url(this.apiUrl).description(this.apiEnvironment)));
	    }
}
