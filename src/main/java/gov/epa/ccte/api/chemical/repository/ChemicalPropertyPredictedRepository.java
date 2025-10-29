package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ChemicalPropertyPredictedRepository extends JpaRepository<ChemicalPropertyPredicted, Long> {
    @Transactional(readOnly = true)
    <T> List<T> findByDtxsidInOrderByDtxsidAsc(String[] dtxsids, Class<T> type);

    @Transactional(readOnly = true)
    <T>List<T> findByDtxsid(String dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    <T> List<T> findByPropNameAndPropValueBetweenOrderByDtxsidAsc(String propertyName, Double valueStart, Double valueEnd, Class<T> type);


    @Query(value = """
    				SELECT prop_name as propertyName
					FROM chemprop.mv_predicted_data
    				GROUP BY prop_name
    				""", nativeQuery = true)
    List<ChemicalPropertyNames> getPredictedPropertiesList();
    
    // *********************** Predicted - End *************************************
    // *********************** Summary - start *************************************

    @Query(value = """
    		SELECT
    			pd.prop_name AS propName,
    			PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY d.prop_value) AS experimentalMedian,
    			PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY pd.prop_value) AS predictedMedian,
    			AVG(d.prop_value) AS experimentalAverage,
    			COUNT(distinct d.prop_value) AS experimentalCount,
    			AVG(pd.prop_value) AS predictedAverage,
    			COUNT(distinct pd.prop_value) AS predictedCount,
    			MIN(d.prop_value) AS experimentalMin,
    			MAX(d.prop_value) AS experimentalMax,
    			MIN(pd.prop_value) AS predictedMin,
    			MAX(pd.prop_value) AS predictedMax,
    			pd.prop_unit AS unit
    		FROM
    			chemprop.mv_predicted_data pd
    		LEFT JOIN
    			chemprop.mv_experimental_data d
    		ON
    			d.dtxsid = pd.dtxsid AND d.prop_name = pd.prop_name
    		WHERE
    			pd.dtxsid = :dtxsid AND pd.prop_category = :propCategory AND pd.prop_value IS NOT NULL
    		GROUP BY
    			pd.prop_name, pd.prop_unit
    				""", nativeQuery = true)
    List<ChemicalPropertySummary> findSummaryByDtxsid(@Param("dtxsid")String dtxsid, @Param("propCategory")String propCategory);
    
    @Query(value = """
    		SELECT
    			pd.prop_name AS propName,
    			PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY d.prop_value) AS experimentalMedian,
    			PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY pd.prop_value) AS predictedMedian,
    			AVG(d.prop_value) AS experimentalAverage,
    			COUNT(distinct d.prop_value) AS experimentalCount,
    			AVG(pd.prop_value) AS predictedAverage,
    			COUNT(distinct pd.prop_value) AS predictedCount,
    			MIN(d.prop_value) AS experimentalMin,
    			MAX(d.prop_value) AS experimentalMax,
    			MIN(pd.prop_value) AS predictedMin,
    			MAX(pd.prop_value) AS predictedMax,
    			pd.prop_unit AS unit
    		FROM
    			chemprop.mv_predicted_data pd
    		LEFT JOIN
    			chemprop.mv_experimental_data d
    		ON
    			d.dtxsid = pd.dtxsid AND d.prop_name = pd.prop_name
    		WHERE
    			pd.dtxsid = :dtxsid AND pd.prop_name = :propName AND  pd.prop_category = :propCategory AND pd.prop_value IS NOT NULL
    		GROUP BY
    			pd.prop_name, pd.prop_unit
    				""", nativeQuery = true)
    List<ChemicalPropertySummary> findSummaryByDtxsidAndPropName(@Param("dtxsid")String dtxsid, @Param("propName")String propName, @Param("propCategory")String propCategory);
    
    @Query(value = """
    		SELECT
    		    d.source_name AS sourceName,
    		    d.source_description AS sourceDescription,
    		    d.public_source_url AS publicSourceUrl,
    		    d.prop_value AS propValue,
    		    d.direct_url AS directUrl,
    		    d.exp_details_species_latin AS speciesLatin,
    		    d.exp_details_response_site AS responseSite,
    		    CASE 
    		    	WHEN d.direct_url IS NULL THEN 'Not Available'
    		    	WHEN d.direct_url IN ('https://ochem.eu/home/show.do', 'https://www.echemportal.org/echemportal/property-search') THEN 'Not Available' 
    		    	ELSE 'Available' 
    		    END AS availability,
    		    CASE 
    		    	WHEN d.direct_url IS NULL THEN FALSE
    		    	WHEN d.direct_url IN ('https://ochem.eu/home/show.do', 'https://www.echemportal.org/echemportal/property-search') THEN FALSE
    		    	ELSE TRUE
			    END AS showLink

    		FROM
    			chemprop.mv_experimental_data d
    		WHERE
    			d.dtxsid = :dtxsid AND d.prop_name = :propName AND  d.prop_category = :propCategory AND d.prop_value IS NOT NULL
    				""", nativeQuery = true)
    List<ChemicalPropertySummaryExperimental> findExpermentalSummaryByDtxsidAndPropName(@Param("dtxsid")String dtxsid, @Param("propName")String propName, @Param("propCategory")String propCategory);
    
    @Query(value = """
    		SELECT
    		    pd.source_name AS sourceName,
    		    pd.source_description AS sourceDescription,
    		    pd.prop_value AS propValue,
    		    CASE
    		    	WHEN r.report_html IS NULL THEN NULL
			    	ELSE CAST('https://ctx-api-dev.ccte.epa.gov/chemical/property/model/reports/html/search/?dtxsid=' || :dtxsid || '&modelId=' || CAST(pd.model_id AS VARCHAR) AS VARCHAR)
    		    END AS link,
    		    CASE 
    		    	WHEN r.report_html IS NULL THEN 'Not Available'
    		    	ELSE 'Available' 
    		    END AS linkAvailability,
    		    CASE 
    		    	WHEN r.report_html IS NULL THEN FALSE
    		    	ELSE TRUE
			    END AS showLink,
			    pd.qmrf_url AS qmrfUrl,
			    CASE 
			    	WHEN pd.qmrf_url IS NULL THEN 'Not Available'
			    	ELSE 'Available' 
			    END AS qmrfAvailability,
			    pd.has_qmrf AS showQmrf

    		FROM
    			chemprop.mv_predicted_data pd
    		LEFT JOIN
    			chemprop.mv_predicted_reports r
    		ON
    			pd.dtxsid = r.dtxsid AND pd.model_id = r.model_id
    		WHERE
    			pd.dtxsid = :dtxsid AND pd.prop_name = :propName AND  pd.prop_category = :propCategory AND pd.prop_value IS NOT NULL
    				""", nativeQuery = true)
    List<ChemicalPropertySummaryPredicted> findPredictedSummaryByDtxsidAndPropName(@Param("dtxsid")String dtxsid, @Param("propName")String propName, @Param("propCategory")String propCategory);
}


