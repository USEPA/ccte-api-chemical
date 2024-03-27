package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import gov.epa.ccte.api.chemical.projection.search.CcdChemicalSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

//@SuppressWarnings("unused")
@RepositoryRestResource(exported = false)
public interface ChemicalSearchRepository extends JpaRepository<ChemicalSearch, Long> {

    <T> List<T> findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(String word, List<String> searchWords, Class<T> type);

    <T> List<T> findTop20ByModifiedValueStartsWithAndSearchNameInOrderByRankAscSearchValueAsc(String modifiedValue, Collection<String> searchNames, Class<T> type);

    <T> List<T> findByModifiedValueOrderByRankAsc(String word, Class<T> type);

    <T> List<T> findByModifiedValueInOrderByRankAsc(Collection<String> modifiedValues, Class<T> type);

    <T> List<T> findByModifiedValueInAndSearchNameInOrderByRankAsc(Collection<String> modifiedValues, Collection<String> searchNames, Class<T> type);


    <T> List<T> findByModifiedValueContainsOrderByRankAscDtxsid(String word, Class<T> type);

    // Query for inchikey suggestion
    @Query("select distinct c.searchValue from ChemicalSearch c where c.modifiedValue like concat(:inchikey, '%')")
    List<String> getInchiKey(@Param("inchikey") String inchikey);

    // Following are defined in chemical search domain class
    @Query(nativeQuery = true)
    List<CcdChemicalSearchResult> equalCcd(String searchWord);

    @Query(nativeQuery = true)
    List<CcdChemicalSearchResult> containCcd(@Param("searchWord") String searchWord);


    // Advance search parameters

    @Query(value = "select distinct ms_ready_dtxsid from ch.v_msready_search where mol_formula = :formula and substance_public is true ", nativeQuery = true)
    List<String> searchMsReadyFormula(String formula);

    @Query(value = "select distinct ms_ready_dtxsid from ch.v_msready_search where input_dtxcid = :dtxcid and substance_public is true", nativeQuery = true)
    List<String> searchMsReadyDtxcid(String dtxcid);

    @Query(value = "select distinct ms_ready_dtxsid from ch.v_msready_search where monoisotopic_mass between :start and :end and substance_public is true", nativeQuery = true)

    List<String> searchMsReadyMass(Double start, Double end);

}
