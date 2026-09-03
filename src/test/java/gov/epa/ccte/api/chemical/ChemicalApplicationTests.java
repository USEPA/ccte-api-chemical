package gov.epa.ccte.api.chemical;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=io.sentry.spring.boot.jakarta.SentryAutoConfiguration"
})
@ActiveProfiles("test")
class ChemicalApplicationTests {
	
	@Test
	void contextLoads() {
	}

	@TestConfiguration
	static class TestCacheConfig {
		@Bean
		CacheManager cacheManager() {
			return new ConcurrentMapCacheManager();
		}
	}


}
