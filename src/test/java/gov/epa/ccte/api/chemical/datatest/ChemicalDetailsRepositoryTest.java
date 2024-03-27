package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailAll;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
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
//@Sql({"/chemical_details_schema.sql","/chemical_details_data.sql"})
public class ChemicalDetailsRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalDetailRepository repository;

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
        assertThat(repository.findByDtxsid("DTXSID7020182", ChemicalDetailAll.class).isPresent()).isEqualTo(true);
    }

    @Test
    void testFindByDtxcid(){
        assertThat(repository.findByDtxcid("DTXCID30182", ChemicalDetailAll.class).isPresent()).isEqualTo(true);
    }

    @Test
    void testFindByDtxsidInOrderByDtxsidAsc(){
        assertThat(repository.findByDtxsidInOrderByDtxsidAsc(new String[]{"DTXSID7020182","DTXSID9020112"}, ChemicalDetailAll.class).size()).isEqualTo(2);
    }

    @Test
    void testFindByDtxcidInOrderByDtxcidAsc(){
        assertThat(repository.findByDtxcidInOrderByDtxcidAsc(new String[]{"DTXCID30182","DTXCID90112"}, ChemicalDetailAll.class).size()).isEqualTo(2);
    }

    @Test
    void testGetMolImageForDtxsid(){
        assertThat(repository.getMolImageForDtxsid("DTXSID7020182")).isNullOrEmpty(); // test data doesn't have mol image
    }

    @Test
    void testGetMolImageForDtxcid(){
        assertThat(repository.getMolImageForDtxcid("DTXCID30182")).isNullOrEmpty(); // test data doesn't have mol image
    }

    @Test
    void testGetMolFileForDtxsid(){
        assertThat(repository.getMolFileForDtxsid("DTXSID7020182").isEmpty()).isEqualTo(true); // test data doesn't have mol file
    }

    @Test
    void testGetMolFileForDtxcid(){
        assertThat(repository.getMolFileForDtxcid("DTXCID30182").isEmpty()).isEqualTo(true); // test data doesn't have mol file
    }

    @Test
    void testGetMrvFileForDtxsid(){
        assertThat(repository.getMrvFileForDtxsid("DTXSID7020182").isEmpty()).isEqualTo(true); // test data doesn't have mrv file
    }

    @Test
    void testGetMrvFileForDtxcid(){
        assertThat(repository.getMrvFileForDtxcid("DTXCID30182").isEmpty()).isEqualTo(true); // test data doesn't have mrv file
    }
}
