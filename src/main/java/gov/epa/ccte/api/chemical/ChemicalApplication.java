package gov.epa.ccte.api.chemical;

import gov.epa.ccte.api.chemical.config.ApplicationProperties;
import gov.epa.ccte.api.chemical.config.Constants;
import gov.epa.ccte.api.chemical.config.DefaultProfileUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class ChemicalApplication {
	private final Environment env;

	public ChemicalApplication(Environment env) {
		this.env = env;
	}

	/**
	 * Initializes chemical.
	 * <p>
	 * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
	 */
	@PostConstruct
	public void initApplication() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if(
				activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) ||
						activeProfiles.contains(Constants.SPRING_PROFILE_STAGE) ||
						activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)
		){
			log.info("application has environment profile");
		}else{
			log.error("application has no environment profile");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		log.info("*** Application is started. ***");

		SpringApplication app = new SpringApplication(ChemicalApplication.class);
		DefaultProfileUtil.addDefaultProfile(app); // dev profile is default
		ConfigurableApplicationContext ctx = app.run(args);
		Environment env = ctx.getEnvironment();

		logApplicationStartup(env);
	}
	private static void logApplicationStartup(Environment env) {
		String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
		String serverPort = env.getProperty("server.port");
		String contextPath = Optional
				.ofNullable(env.getProperty("server.servlet.context-path"))
				.filter(StringUtils::isNotBlank)
				.orElse("/");
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}{}\n\t" +
						"External: \t{}://{}:{}{}\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				serverPort,
				contextPath,
				protocol,
				hostAddress,
				serverPort,
				contextPath,
				env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
		);

		String configServerStatus = env.getProperty("configserver.status");
		if (configServerStatus == null) {
			configServerStatus = "Not found or not setup for this application";
		}
		log.info(
				"\n----------------------------------------------------------\n\t" +
						"Config Server: \t{}\n----------------------------------------------------------",
				configServerStatus
		);
	}
}
