package gov.epa.ccte.api.chemical.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Computational Toxicology and Exposure (CTX) APIs - CTX Chemical API",
                description = "The CTX Chemical API is part of US EPA's Computational Toxicology and Exposure APIs. The CTX Chemical API provides programmtic access to chemical data through a set of endpoints.",
                contact = @Contact(
                        name = "",
                        url = "",
                        email = ""),
                version = "1.1.0"
                ),
        servers = { @Server(url = "${application.api-url}", description = "${application.api-env}")}
)
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "api_key",
        in = SecuritySchemeIn.HEADER,
        description = "Each API request requires an api_key. Contact the CTX API Admin to request an API Key.",
        paramName = "x-api-key"
)
public class OpenApiConfig {
}
