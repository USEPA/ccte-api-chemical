package gov.epa.ccte.api.chemical.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
public class SentryController {

	private static final Logger log = LoggerFactory.getLogger(SentryController.class);

	// Simple health endpoint to verify controller is reachable
	@GetMapping("/sentry/test")
	public ResponseEntity<String> sentryTest() {
		log.info("/sentry/test called");
		return ResponseEntity.ok("sentry test ok");
	}

	// Endpoint that throws an exception to exercise Sentry error capture
	@GetMapping("/sentry/error")
	public ResponseEntity<String> sentryError() {
		log.info("/sentry/error called - about to throw exception for Sentry testing");
		throw new RuntimeException("Sentry test exception");
	}

}
