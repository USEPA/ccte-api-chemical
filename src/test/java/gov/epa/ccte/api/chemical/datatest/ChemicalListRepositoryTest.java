package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListAll;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
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
public class ChemicalListRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalListRepository repository;

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
    void testFindByVisibilityOrderByListNameAsc(){
        assertThat(repository.findByVisibilityAndIsVisibleOrderByTypeAscListNameAsc("PUBLIC", true, ChemicalListAll.class).size()).isEqualTo(2);
    }

    @Test
    void testFindByType(){
        assertThat(repository.findByTypeAndVisibilityAndIsVisibleOrderByListNameAsc("federal", "PUBLIC", true, ChemicalListAll.class).size()).isEqualTo(1);
    }

    @Test
    void testFindByListName(){
        assertThat(repository.findByListNameIgnoreCaseAndVisibilityAndIsVisible("MICROCYSTIN", "PUBLIC", true, ChemicalListAll.class).isPresent()).isTrue();
    }

    @Test
    void testGetAllTypes(){
        // test data only has two types 'other' and 'federal'
        assertThat(repository.getAllTypes().size()).isEqualTo(2);
    }
}
