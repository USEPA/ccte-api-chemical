package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ChemicalSearchRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalSearchRepository repository;

    private final List<String> searchMatchWithoutInchikey = Arrays.asList("Deleted CAS-RN","PC-Code","Substance_id","Approved Name","Alternate CAS-RN",
            "CAS-RN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
            "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id", "EHCA Number", "EC Number");
    private final List<String> searchMatchAll = Arrays.asList("Deleted CAS-RN","PC-Code","Substance_id","Approved Name","Alternate CAS-RN",
            "CAS-RN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
            "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id",
            "InChIKey", "Indigo InChIKey", "EHCA Number", "EC Number");

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }
    @Test
    void testDataLoaded(){
        assertThat(repository.findAll().size()).isEqualTo(7);
    }

    @Test
    void testFindByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(){
        assertThat(repository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue("BPA", searchMatchWithoutInchikey, ChemicalSearchAll.class).size()).isEqualTo(1);
    }

    @Test
    void testFindByModifiedValueOrderByRankAsc(){
        assertThat(repository.findByModifiedValueOrderByRankAsc("BPA", ChemicalSearchAll.class).size()).isEqualTo(1);
    }

    @Test
    void testFindByModifiedValueContainsOrderByRankAscDtxsidAsc(){
        assertThat(repository.findByModifiedValueContainsOrderByRankAscDtxsid("BPA", ChemicalSearchAll.class).size()).isEqualTo(1);
    }
}
