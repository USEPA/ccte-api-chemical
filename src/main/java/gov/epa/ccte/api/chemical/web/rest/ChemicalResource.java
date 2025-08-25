package gov.epa.ccte.api.chemical.web.rest;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for getting the {@link ChemicalResource}s.
 */
@Slf4j
@RestController
@CrossOrigin
@Hidden // OpenAPI annotation for hidding endpoints from documentation generator
public class ChemicalResource {

    private final JdbcTemplate jdbcTemplate;

    public ChemicalResource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/chemical/health")
    public ResponseEntity health(){

        log.info("checking the health");

        if(jdbcTemplate != null){
            try {
                jdbcTemplate.execute("SELECT 1 ");
                log.debug("DB connection established");

                return ResponseEntity.ok().build();

            } catch (Exception ep){
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
