package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalSynonym;
import gov.epa.ccte.api.chemical.projection.CcdSynonymFlatProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "chemicalSynonym", path = "chemical-synonym", itemResourceRel = "chemicalSynonym", exported = false)
public interface ChemicalSynonymRepository extends JpaRepository<ChemicalSynonym, String> {

	@Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    <T> Optional<T> findByDtxsid(String dtxsid, Class<T> type);
    
    <T> Optional<T> findByDtxsidAndIsPublic(String dtxsid, Boolean isPublic, Class<T> type);
    
    <T> List<T> findByDtxsidInAndIsPublicOrderByDtxsidAsc(Collection<String> dtxsids, Boolean isPublic, Class<T> type);
    
    @Query(nativeQuery = true,
            value = "SELECT unnest(string_to_array(valid_synonym, '|')) AS synonym, 'valid_synonym' AS quality FROM ch.v_chemical_snonyms WHERE dtxsid = :dtxsid " +
                    "UNION ALL " +
                    "SELECT unnest(string_to_array(good_synonym, '|')) AS synonym, 'good_synonym' AS quality FROM ch.v_chemical_snonyms WHERE dtxsid = :dtxsid " +
                    "UNION ALL " +
                    "SELECT unnest(string_to_array(deleted_synonym, '|')) AS synonym, 'deleted_synonym' AS quality FROM ch.v_chemical_snonyms WHERE dtxsid = :dtxsid " +
                    "UNION ALL " +
                    "SELECT unnest(string_to_array(other_synonym, '|')) AS synonym, 'other_synonym' AS quality FROM ch.v_chemical_snonyms WHERE dtxsid = :dtxsid " +
                    "UNION ALL " +
                    "SELECT unnest(string_to_array(beilstein_synonym, '|')) AS synonym, 'beilstein_synonym' AS quality FROM ch.v_chemical_snonyms WHERE dtxsid = :dtxsid " +
                    "UNION ALL " +
                    "SELECT unnest(string_to_array(alternate_synonym, '|')) AS synonym, 'alternate_synonym' AS quality FROM ch.v_chemical_snonyms WHERE dtxsid = :dtxsid")
     List<CcdSynonymFlatProjection> getFlatSynonymsByDtxsid(@Param("dtxsid") String dtxsid);
    

}