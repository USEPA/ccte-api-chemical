package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.dto.ChemicalFateAllDto;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ChemicalPropertyExperimentalRepository extends JpaRepository<ChemicalPropertyExperimental, Long> {

    @Transactional(readOnly = true)
    <T> List<T> findByDtxsidInOrderByDtxsidAsc(String[] dtxsids, Class<T> type);

    @Transactional(readOnly = true)
    <T>List<T> findByDtxsid(String dtxsid, Class<T> type);

    
    @Transactional(readOnly = true)
    <T> List<T> findByPropNameAndPropValueBetweenOrderByDtxsidAsc(String propertyId, Double valueStart, Double valueEnd, Class<T> type);


    @Query(value = """
			SELECT prop_name as propertyName
			FROM chemprop.mv_experimental_data
			GROUP BY prop_name
			""", nativeQuery = true)
    List<ChemicalPropertyNames> getExperimentalPropertiesList();
    
    // *********************** Experimental - End *************************************
    // *********************** Fate - Start *************************************
    
    @Query(value = """
    	    WITH experimentalFate AS (
    	        SELECT ex.prop_name, row_to_json(ex) AS data
    	        FROM chemprop.mv_experimental_data ex
    	        WHERE ex.dtxsid IN (:dtxsid) AND ex.prop_category = 'Env. Fate/transport'
    	    ),
    	    predictedFate AS (
    	        SELECT pd.prop_name, row_to_json(pd) AS data
    	        FROM chemprop.mv_predicted_data pd
    	        WHERE pd.dtxsid IN (:dtxsid) AND pd.prop_category = 'Env. Fate/transport'
    	    )
    	    SELECT 
    	        COALESCE(ef.prop_name, pf.prop_name) AS propName,
    	        COALESCE(json_agg(ef.data), '[]'::json) AS experimentalFateData,
    	        COALESCE(json_agg(pf.data), '[]'::json) AS predictedFateData
    	    FROM experimentalFate ef
    	    FULL OUTER JOIN predictedFate pf ON ef.prop_name = pf.prop_name
    	    GROUP BY COALESCE(ef.prop_name, pf.prop_name)
    	    """, nativeQuery = true)
    	List<ChemicalFateAllDto> findFateByDtxsid(String dtxsid);
    
    
    @Query(value = """
    	    SELECT dtxsid,
    	           json_agg(json_build_object(
    	               'propName', prop_name,
    	               'experimentalFateData', experimentalFateData,
    	               'predictedFateData', predictedFateData
    	           )) AS properties
    	    FROM (
    	        SELECT COALESCE(ef.dtxsid, pf.dtxsid) AS dtxsid,
    	               COALESCE(ef.prop_name, pf.prop_name) AS prop_name,
    	               COALESCE(json_agg(ef.data) FILTER (WHERE ef.data IS NOT NULL), '[]'::json) AS experimentalFateData,
    	               COALESCE(json_agg(pf.data) FILTER (WHERE pf.data IS NOT NULL), '[]'::json) AS predictedFateData
    	        FROM (
    	            SELECT ex.dtxsid, ex.prop_name, row_to_json(ex) AS data
    	            FROM chemprop.mv_experimental_data ex
    	            WHERE ex.dtxsid IN (:dtxsids) AND ex.prop_category = 'Env. Fate/transport'
    	        ) ef
    	        FULL OUTER JOIN (
    	            SELECT pd.dtxsid, pd.prop_name, row_to_json(pd) AS data
    	            FROM chemprop.mv_predicted_data pd
    	            WHERE pd.dtxsid IN (:dtxsids) AND pd.prop_category = 'Env. Fate/transport'
    	        ) pf
    	        ON ef.dtxsid = pf.dtxsid AND ef.prop_name = pf.prop_name
    	        GROUP BY COALESCE(ef.dtxsid, pf.dtxsid), COALESCE(ef.prop_name, pf.prop_name)
    	    ) sub
    	    GROUP BY dtxsid
    	    ORDER BY dtxsid ASC
    	    """, nativeQuery = true)
    	List<Object[]> findFateByDtxsidInOrderByDtxsidAsc(String[] dtxsids);
    
}
