package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.ChemicalPropertyAll;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyRepository;
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
public class ChemicalPropertyRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalPropertyRepository repository;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }
    @Test
    void testDataLoaded(){
        assertThat(repository.findAll().size()).isEqualTo(18);
    }

    @Test
    void testGetPredictedProperty(){
        assertThat(repository.getPredictedPropertiesList().size()).isEqualTo(1);
    }

    @Test
    void testGetExperimentalProperty(){
        assertThat(repository.getExperimentalPropertiesList().size()).isEqualTo(1);
    }

    @Test
    void testFindByDtxsid(){
        assertThat(repository.findByDtxsid("DTXSID7020182", ChemicalPropertyAll.class).size()).isEqualTo(9);
    }

    @Test
    void testFindByDtxsidAndPropTypeOrderByName(){
        assertThat(repository.findByDtxsidAndPropTypeOrderByName("DTXSID7020182", "experimental", ChemicalPropertyAll.class).size()).isEqualTo(6);
    }

    @Test
    void testFindByDtxsidInOrderByDtxsidAscPropTypeAscNameAsc(){
        // this is use for batch search
        assertThat(repository.findByDtxsidInOrderByDtxsidAscPropTypeAscNameAsc(new String[]{"DTXSID7020182", "DTXSID9020112"}, ChemicalPropertyAll.class).size()).isEqualTo(18);
    }

    @Test
    void testFindByPropertyIdAndValueBetweenOrderByDtxsidAsc(){

        assertThat(repository.findByPropertyIdAndValueBetweenOrderByDtxsidAsc("melting-point", 153.0, 157.0, ChemicalPropertyAll.class).size()).isEqualTo(7);
    }

}
