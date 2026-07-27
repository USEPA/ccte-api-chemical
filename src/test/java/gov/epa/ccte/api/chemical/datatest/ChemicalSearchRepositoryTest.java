package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.search.CcdChemicalSearchResult;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Limit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJpaTest
@ActiveProfiles("test")
public class ChemicalSearchRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalSearchRepository repository;

    private final List<String> searchMatchWithoutInchikey = Arrays.asList("Deleted CAS-RN", "PC-Code", "Substance_id", "Approved Name", "Alternate CAS-RN",
            "CAS-RN", "Synonym", "Integrated Source CAS-RN", "DSSTox_Compound_Id", "Systematic Name", "Integrated Source Name",
            "Expert Validated Synonym", "Synonym from Valid Source", "FDA CAS-Like Identifier", "DSSTox_Substance_Id", "EHCA Number", "EC Number");
    private final List<String> searchMatchAll = Arrays.asList("Deleted CAS-RN", "PC-Code", "Substance_id", "Approved Name", "Alternate CAS-RN",
            "CAS-RN", "Synonym", "Integrated Source CAS-RN", "DSSTox_Compound_Id", "Systematic Name", "Integrated Source Name",
            "Expert Validated Synonym", "Synonym from Valid Source", "FDA CAS-Like Identifier", "DSSTox_Substance_Id",
            "InChIKey", "Indigo InChIKey", "EHCA Number", "EC Number");

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }

    @Test
    void testFindByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue() {
        assertThat(repository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue("BPA", searchMatchWithoutInchikey, Limit.unlimited(), ChemicalSearchAll.class).size()).isEqualTo(0);
    }

    @Test
    void testFindByModifiedValueOrderByRankAsc() {
        assertThat(repository.findByModifiedValueOrderByRankAsc("BPA", ChemicalSearchAll.class).size()).isEqualTo(0);
    }

    @Test
    void testFindByModifiedValueContainsOrderByRankAscDtxsidAsc() {
        assertThat(repository.findByModifiedValueContainsOrderByRankAscDtxsid("BPA", Limit.unlimited(), ChemicalSearchAll.class).size()).isEqualTo(0);
    }

    @Test
    void equalCcd_executesSqlResultSetMapping_and_bindsDecimalPercentAssays() {
        // Seed the schema/table actually used by the native query subselect
        jdbcTemplate.update(
                """
                    INSERT INTO ch.v_chemical_search
                    (id, dtxsid, dtxcid, casrn, smiles, preferred_name, search_group, search_name,
                     search_value, modified_value, rank, has_structure_image, is_markush, created_by, created_at)
                    VALUES (900001, 'DTXSID9020112', 'DTXCID90112', '1912-24-9',
                            'CCNC1=NC(NC(C)C)=NC(Cl)=N1', 'Atrazine', 'CASRN', 'CASRN',
                            '1912-24-9', '1912-24-9', 5, 1, false, 'test', CURRENT_TIMESTAMP)
                """);

        // Force decimal content in the numeric column
        jdbcTemplate.update(
                """
                    UPDATE ms.chemical_details
                    SET percent_assays = CAST(6.25 AS DECIMAL(10,2))
                    WHERE dtxsid = 'DTXSID9020112'
                """);

        entityManager.flush();
        entityManager.clear();

        List<CcdChemicalSearchResult> results = repository.equalCcd("1912-24-9");

        assertThat(results).isNotEmpty();
        CcdChemicalSearchResult first = results.get(0);

        // This assertion catches constructor/mapping drift on generic_substance_id
        assertThat(first.getGenericSubstanceId()).isEqualTo(20112);

        // This assertion catches Integer/BigInteger vs BigDecimal mapping drift
        assertThat(first.getPercentAssays()).isEqualByComparingTo(new BigDecimal("6.25"));
    }

    @Test
    void equalCcd_doesNotThrowInstantiationException() {
        jdbcTemplate.update(
                """
                    INSERT INTO ch.v_chemical_search
                    (id, dtxsid, dtxcid, casrn, smiles, preferred_name, search_group, search_name,
                     search_value, modified_value, rank, has_structure_image, is_markush, created_by, created_at)
                    VALUES (900002, 'DTXSID9020112', 'DTXCID90112', '1912-24-9',
                            'CCNC1=NC(NC(C)C)=NC(Cl)=N1', 'Atrazine', 'CASRN', 'CASRN',
                            '1912-24-9', '1912-24-9', 5, 1, false, 'test', CURRENT_TIMESTAMP)
                """);

        assertThatCode(() -> repository.equalCcd("1912-24-9"))
                .doesNotThrowAnyException();
    }

}
