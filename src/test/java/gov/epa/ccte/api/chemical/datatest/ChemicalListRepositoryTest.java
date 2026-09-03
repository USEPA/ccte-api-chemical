package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql","/data.sql"})
public class ChemicalListRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalListRepository repository;
    @Autowired private ChemicalListChemicalRepository chemicalListChemicalRepository;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
        assertThat(chemicalListChemicalRepository).isNotNull();
    }

    @Test
    void testDataLoaded(){
        assertThat(repository.findAll().size()).isEqualTo(10);
    }

    @Test
    void testFindByVisibilityOrderByListNameAsc(){
        assertThat(repository.findAllOrderByTypeAscAndListNameAsc(ChemicalList.class).size()).isEqualTo(4);
    }

    @Test
    void testFindByType(){
        assertThat(repository.findByTypeIgnoreCaseOrderByListNameAsc("federal", ChemicalList.class).size()).isEqualTo(3);
    }


    @Test
    void testGetAllTypes(){
        // test data only has two types 'other' and 'federal'
        assertThat(repository.getAllTypes().size()).isEqualTo(2);
    }
}
