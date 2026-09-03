package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.dto.ChemicalListWithDtxsidsDTO;
import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ChemicalListRepository extends JpaRepository<ChemicalList, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT c FROM ChemicalList c ORDER BY c.type ASC, c.listName ASC")
    <T>List<T> findAllOrderByTypeAscAndListNameAsc(Class<T> type);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM ChemicalList c ORDER BY c.listName ASC")
    <T>List<T> findAllOrderByListNameAsc(Class<T> type);
    
    @Transactional(readOnly = true)
    <T>List<T> findByTypeIgnoreCaseOrderByListNameAsc(String listType, Class<T> type);


    @Transactional(readOnly = true)
    Optional<ChemicalList> findByListNameIgnoreCase(String listName);

    @Transactional(readOnly = true)
    <T>List<T> findByListNameInIgnoreCaseOrderByListNameAsc(Collection<String> listNames, Class<T> type);


    @Transactional(readOnly = true)
    @Cacheable("listTypeNames")
    @Query("SELECT distinct type from ChemicalList order by type")
    List<String> getAllTypes();

    @Transactional(readOnly = true)
    @NativeQuery("""
    				SELECT 
    					l.list_name as listName, 
    					l.label,
					    l.type, 
					    l.short_description as shortDescription, 
					    l.long_description as longDescription, 
					    l.chemical_count as chemicalCount, 
					    l.updated_at as updatedAt, 
					    l.id, 
					    string_agg(distinct c.dtxsid,',') as dtxsids
					FROM
                        ch.v_chemical_lists_new l 
                    JOIN 
                        ch.v_chemical_list_chemicals_new c 
                    ON
					    l.id = c.list_id 
					WHERE 
					    upper(l.list_name) = upper(:listName)
                    GROUP BY 
    		            l.list_name, l.type, l.label, l.short_description, l.long_description, l.chemical_count, l.updated_at, l.id
                    
    		    """)
    Optional<ChemicalListWithDtxsidsDTO> getListWithDtxsidsByListName(String listName);

    @Transactional(readOnly = true)
    @NativeQuery("""
    				SELECT 
    					l.list_name as listName,
    					l.label, 
					    l.type, 
					    l.short_description as shortDescription, 
					    l.long_description as longDescription, 
					    l.chemical_count as chemicalCount, 
					    l.updated_at as updatedAt, 
					    l.id, 
					    string_agg(distinct c.dtxsid,',') as dtxsids 
					FROM 
						ch.v_chemical_lists_new l 
					JOIN 
						ch.v_chemical_list_chemicals_new c 
					ON 
						l.id = c.list_id 
					WHERE 
						l.type = :type 
					GROUP BY 
						l.list_name, l.type, l.label, l.short_description, l.long_description, l.chemical_count, l.updated_at, l.id
					Order BY 
					   l.list_name
				""")
    List<ChemicalListWithDtxsidsDTO> getListsWithDtxsidsByType(String type);

    @Transactional(readOnly = true)
    @NativeQuery("""
            		SELECT 
            		    l.list_name as listName,
            		    l.label, 
            		    l.type, l.short_description as shortDescription, 
            		    l.long_description as longDescription, 
            		    l.chemical_count as chemicalCount,
            		    l.updated_at as updatedAt, 
            		    l.id, 
            		    string_agg(distinct c.dtxsid,',') as dtxsids
                    FROM
                        ch.v_chemical_lists_new l 
                    JOIN 
                        ch.v_chemical_list_chemicals_new c 
                    ON
                       l.id = c.list_id
                    GROUP BY 
                       l.list_name, l.type, l.label, l.short_description, l.long_description, l.chemical_count,l.updated_at, l.id
                    Order BY 
					   l.type, l.list_name
                """)
    List<ChemicalListWithDtxsidsDTO> getListsWithDtxsids();

	@Transactional(readOnly = true)
	@NativeQuery("""
			    SELECT *
			    FROM (
			        SELECT DISTINCT ON (l.list_name)
			        	l.id,
			            l.list_name,
			            l.label,
			            l.type,
			            l.short_description,
			            l.long_description,
			            l.chemical_count,
			            l.updated_at
			        FROM 
			        	ch.v_chemical_lists_new l
			        JOIN 
			        	ch.v_chemical_list_chemicals_new c 
			        ON
			            l.id = c.list_id AND c.dtxsid = :dtxsid
			        ORDER BY 
			        	l.list_name, l.updated_at DESC) latest_lists
			    ORDER BY 
			    	latest_lists.type, latest_lists.list_name
			""")
    List<ChemicalList> getListsByDtxsid(String dtxsid);
	
	@Transactional(readOnly = true)
	@NativeQuery("""
			    SELECT *
			    FROM (
			        SELECT DISTINCT ON (l.list_name)
			            l.list_name
			        FROM 
			        	ch.v_chemical_lists_new l
			        JOIN 
			        	ch.v_chemical_list_chemicals_new c 
			        ON
			            l.id = c.list_id AND c.dtxsid = :dtxsid
			        ORDER BY 
			        	l.list_name, l.updated_at DESC) latest_lists
			    ORDER BY 
			    	latest_lists.list_name
			""")
    List<ChemicalListName> getListNamesByDtxsid(String dtxsid);


    @Transactional(readOnly = true)
    @NativeQuery("""
		    SELECT *
		    FROM (
		        SELECT DISTINCT ON (l.list_name)
		            l.list_name,
		            l.label,
		            l.type,
		            l.short_description,
		            l.long_description,
		            l.chemical_count,
		            l.updated_at
		        FROM 
    		        ch.v_chemical_lists_new l
		        JOIN 
    		        ch.v_chemical_list_chemicals_new c 
    		    ON
		            l.id = c.list_id AND c.dtxsid = :dtxsid
		        ORDER BY 
    		        l.list_name, l.updated_at DESC) latest_lists
		    ORDER BY 
    		    latest_lists.type, latest_lists.list_name
		""")
    List<?> getListsByDtxsidCcd(String dtxsid);

}