package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListWithDtxsids;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ChemicalListRepository extends JpaRepository<ChemicalList, Integer> {

    @Transactional(readOnly = true)
    <T> List<T> findByVisibilityAndIsVisibleOrderByTypeAscListNameAsc(String visibility, Boolean isVisible, Class<T> type);

    @Transactional(readOnly = true)
    <T> List<T> findByTypeAndVisibilityAndIsVisibleOrderByListNameAsc(String listType, String visibility, Boolean isVisible, Class<T> type);


    @Transactional(readOnly = true)
    <T>
    Optional<T> findByListNameIgnoreCaseAndVisibilityAndIsVisible(String listName, String visibility, Boolean isVisible, Class<T> type);


    @Transactional(readOnly = true)
    @Cacheable("listTypeNames")
    @Query("SELECT distinct type from ChemicalList order by type")
    List<String> getAllTypes();

    @Transactional(readOnly = true)
    @Query( nativeQuery = true,
            value = "select l.list_name as listName, l.label, l.type, l.visibility, l.short_description as shortDescription, l.long_description as longDescription, l.chemical_count as chemicalCount, " +
                    " l.created_at as createdAt, l.updated_at as updatedAt, l.id, string_agg(c.dtxsid,',') as dtxsids " +
                    " from ch.v_chemical_lists l join ch.v_chemical_list_chemicals c on " +
                    " l.id = c.list_id and upper(l.list_name) = upper(:listName) and l.visibility = :visibility and l.is_visible = true and c.is_public = true " +
                    " group by l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at, l.id")
    Optional<ChemicalListWithDtxsids> getListWithDtxsidsByListName(String listName, String visibility);

    @Transactional(readOnly = true)
    @Query( nativeQuery = true,
            value = "select l.list_name as listName, l.label, l.type, l.visibility, l.short_description as shortDescription, l.long_description as longDescription, l.chemical_count as chemicalCount, " +
                    " l.created_at as createdAt, l.updated_at as updatedAt, l.id, string_agg(c.dtxsid,',') as dtxsids " +
                    " from ch.v_chemical_lists l join ch.v_chemical_list_chemicals c on " +
                    " l.id = c.list_id and l.type = :type and l.visibility = :visibility and l.is_visible = true and c.is_public = true "+
                    " group by l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at, l.id")
    List<ChemicalListWithDtxsids> getListsWithDtxsidsByType(String type, String visibility);

    @Transactional(readOnly = true)
    @Query( nativeQuery = true,
            value = "select l.list_name as listName, l.label, l.type, l.visibility, l.short_description as shortDescription, l.long_description as longDescription, l.chemical_count as chemicalCount, " +
                    " l.created_at as createdAt, l.updated_at as updatedAt, l.id, string_agg(c.dtxsid,',') as dtxsids " +
                    " from ch.v_chemical_lists l join ch.v_chemical_list_chemicals c on " +
                    " l.id = c.list_id and l.visibility = :visibility and l.is_visible = true and c.is_public = true " +
                    " group by l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at, l.id")
    List<ChemicalListWithDtxsids> getListsWithDtxsids(String visibility);

    @Transactional(readOnly = true)
    @Query( nativeQuery = true,
            value = "select l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at " +
                    " from ch.v_chemical_lists l join ch.v_chemical_list_chemicals c on " +
                    " l.id = c.list_id and c.dtxsid = :dtxsid and l.visibility = :visibility and l.is_visible = true and c.is_public = true " +
                    " order by l.type, l.list_name, l.label  " )
    List getListsByDtxsid(String dtxsid,String visibility);

    @Transactional(readOnly = true)
    @Query( nativeQuery = true,
            value = "select l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at, l.id, string_agg(c.dtxsid,',') as dtxsids " +
                    " from ch.v_chemical_lists l join ch.v_chemical_list_chemicals c on " +
                    " l.id = c.list_id and c.dtxsid = :dtxsid and l.visibility = :visibility and l.is_visible = true and c.is_public = true" +
                    " group by l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at, l.id")
    List getListsByDtxsidWithDtxsids(String dtxsid,String visibility);

}
