package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RepositoryRestResource(collectionResourceRel = "chemicalDetails", path = "chemical-details", itemResourceRel = "chemicalDetail", exported = false)
public interface ChemicalDetailRepository extends JpaRepository<ChemicalDetail, Long> {

    // Single chemical search
    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = true)
    <T>
    Optional<T> findByDtxsid(@Param("dtxsid") String dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxcid", path = "by-dtxcid", exported = true)
    <T>
    Optional<T> findByDtxcid(String dtxcid, Class<T> type);

    // Batch search
    @Transactional(readOnly = true)
    <T>
    List<T> findByDtxsidInOrderByDtxsidAsc(String[] dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    <T>
    List<T> findByDtxcidInOrderByDtxcidAsc(String[] dtxsid, Class<T> type);

    // Query for chemical files
    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.molImage from ChemicalDetail c where c.dtxsid = :dtxsid")
    byte[] getMolImageForDtxsid(@Param("dtxsid") String dtxsid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.molImage from ChemicalDetail c where c.dtxcid = :dtxcid")
    byte[] getMolImageForDtxcid(@Param("dtxcid") String dtxcid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.molFile from ChemicalDetail c where c.dtxsid = :dtxsid")
    Optional<String> getMolFileForDtxsid(@Param("dtxsid") String dtxsid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.molFile from ChemicalDetail c where c.dtxcid = :dtxcid")
    Optional<String> getMolFileForDtxcid(@Param("dtxcid") String dtxcid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.mrvFile from ChemicalDetail c where c.dtxsid = :dtxsid")
    Optional<String> getMrvFileForDtxsid(@Param("dtxsid") String dtxsid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.mrvFile from ChemicalDetail c where c.dtxcid = :dtxcid")
    Optional<String> getMrvFileForDtxcid(@Param("dtxcid") String dtxcid);

}
