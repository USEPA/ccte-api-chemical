package gov.epa.ccte.api.chemical.repository;
import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import gov.epa.ccte.api.chemical.projection.search.CcdChemicalSearchResult;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Collection;
import java.util.List;

//@SuppressWarnings("unused")
@RepositoryRestResource(exported = false)
public interface ChemicalSearchRepository extends JpaRepository<ChemicalSearch, Long> {
    
	<T> List<T> findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(String word, List<String> searchWords, Limit limit, Class<T> type);
    
	<T> List<T> findByModifiedValueOrderByRankAsc(String word, Class<T> type);
    
	<T> List<T> findByModifiedValueInOrderByRankAsc(Collection<String> modifiedValues, Class<T> type);
    
	<T> List<T> findByModifiedValueInAndSearchNameInOrderByRankAsc(Collection<String> modifiedValues, Collection<String> searchNames, Class<T> type);
    
	<T> List<T> findByModifiedValueContainsAndSearchNameInOrderByRankAscDtxsidAsc(String modifiedValue, List<String> searchWords, Limit limit, Class<T> type);

    <T> List<T> findByModifiedValueContainsOrderByRankAscDtxsid(String word, Limit limit, Class<T> type);
    
    // Query for inchikey suggestion
    @Query("select distinct c.searchValue from ChemicalSearch c where c.modifiedValue like concat(:inchikey, '%')")
    List<String> getInchiKey(@Param("inchikey") String inchikey);
    // Following are defined in chemical search domain class
    
    @NativeQuery
    List<CcdChemicalSearchResult> equalCcd(String searchWord);
    
    @NativeQuery
    List<CcdChemicalSearchResult> containCcd(@Param("searchWord") String searchWord);
    // Advance search parameters
    
    @NativeQuery("select distinct ms_ready_dtxsid from ch.v_msready_search where mol_formula = :formula")
    List<String> searchMsReadyFormula(String formula);

    @NativeQuery("select distinct ms_ready_dtxsid from ch.v_msready_search where input_dtxcid = :dtxcid")
    List<String> searchMsReadyDtxcid(String dtxcid);

    @NativeQuery("select distinct ms_ready_dtxsid from ch.v_msready_search where input_dtxcid in :dtxcid")
    List<String> searchMsReadyByBatchDtxcid(String[] dtxcid);

    @NativeQuery("select distinct ms_ready_dtxsid from ch.v_msready_search where monoisotopic_mass between :start and :end")
    List<String> searchMsReadyMass(Double start, Double end);
    
    @NativeQuery("select distinct dtxsid from ch.v_chemical_details where  monoisotopic_mass between :start and :end AND is_markush is false")
    List<String> getMassValues(Double start, Double end);
    
    @NativeQuery("select distinct dtxsid from ch.v_chemical_details where mol_formula = :formula AND is_markush is false")
    List<String> getExactFormula(String formula);
    
    @NativeQuery("select distinct dtxsid from ch.v_chemical_details where mol_formula in :formulas AND is_markush is false")
    List<String> getExactFormulaBatch(List<String> formulas);
    
    @NativeQuery("select count(distinct dtxsid) from ch.v_chemical_details where mol_formula = :formula AND is_markush is false")
    Long getExactFormulaCount(String formula);
    
    @NativeQuery("select distinct ms_ready_dtxsid from ch.v_msready_search where mol_formula = :formula")
    List<String> searchAllMsReadyFormula(String formula);
    
    @NativeQuery("select distinct ms_ready_dtxsid from ch.v_msready_search where mol_formula in :formulas")
    List<String> searchAllByBatchMsReadyFormula(List<String> formulas);
    
    @NativeQuery("select count(distinct ms_ready_dtxsid) from ch.v_msready_search where mol_formula = :formula")
    Long getMsReadyFormulaCount(String formula);

}
