package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.ChemicalSynonymAll;
import gov.epa.ccte.api.chemical.repository.ChemicalSynonymRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ChemicalSynonymRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalSynonymRepository repository;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }
    @Test
    void testDataLoaded(){
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void testFindByDtxsid(){
        assertThat(repository.findByDtxsid("DTXSID9020112", ChemicalSynonymAll.class).isPresent()).isTrue();
    }
}
