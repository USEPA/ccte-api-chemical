package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
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


    @NativeQuery("""
    				SELECT prop_name as propertyName
					FROM chemprop.mv_predicted_data
    				GROUP BY prop_name
    				""")
    List<ChemicalPropertyNames> getPredictedPropertiesList();
    
    // *********************** Predicted - End *************************************
    // *********************** Summary - start *************************************

    @NativeQuery("""
    		WITH combined AS (
    			SELECT
    				prop_name,
    				prop_value,
    				prop_unit,
    				prop_category,
    				dtxsid,
    				'predicted' AS source
    			FROM chemprop.mv_predicted_data
    			WHERE dtxsid = :dtxsid AND prop_category = :propCategory AND prop_value IS NOT NULL
    			UNION ALL
    			SELECT
    				prop_name,
    				prop_value,
    				prop_unit,
    				prop_category,
    				dtxsid,
    				'experimental' AS source
    			FROM chemprop.mv_experimental_data
    			WHERE dtxsid = :dtxsid AND prop_category = :propCategory AND prop_value IS NOT NULL
    		)
    		SELECT
    			prop_name AS propName,
    			PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalMedian,
    			PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY CASE WHEN source = 'predicted' THEN prop_value END) AS predictedMedian,
    			AVG(CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalAverage,
    			COUNT(DISTINCT CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalCount,
    			AVG(CASE WHEN source = 'predicted' THEN prop_value END) AS predictedAverage,
    			COUNT(DISTINCT CASE WHEN source = 'predicted' THEN prop_value END) AS predictedCount,
    			MIN(CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalMin,
    			MAX(CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalMax,
    			MIN(CASE WHEN source = 'predicted' THEN prop_value END) AS predictedMin,
    			MAX(CASE WHEN source = 'predicted' THEN prop_value END) AS predictedMax,
    			prop_unit AS unit
    		FROM combined
    		GROUP BY prop_name, prop_unit
    	    """)
    	List<ChemicalPropertySummary> findSummaryByDtxsid(@Param("dtxsid")String dtxsid, @Param("propCategory")String propCategory);
    
    @NativeQuery("""
    	    WITH combined AS (
    	        SELECT
    	            prop_name,
    	            prop_value,
    	            prop_unit,
    	            prop_category,
    	            dtxsid,
    	            'predicted' AS source
    	        FROM chemprop.mv_predicted_data
    	        WHERE dtxsid = :dtxsid AND prop_category = :propCategory AND prop_value IS NOT NULL AND prop_name = :propName
    	        UNION ALL
    	        SELECT
    	            prop_name,
    	            prop_value,
    	            prop_unit,
    	            prop_category,
    	            dtxsid,
    	            'experimental' AS source
    	        FROM chemprop.mv_experimental_data
    	        WHERE dtxsid = :dtxsid AND prop_category = :propCategory AND prop_value IS NOT NULL AND prop_name = :propName
    	    )
    	    SELECT
    	        prop_name AS propName,
    	        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalMedian,
    	        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY CASE WHEN source = 'predicted' THEN prop_value END) AS predictedMedian,
    	        AVG(CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalAverage,
    	        COUNT(DISTINCT CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalCount,
    	        AVG(CASE WHEN source = 'predicted' THEN prop_value END) AS predictedAverage,
    	        COUNT(DISTINCT CASE WHEN source = 'predicted' THEN prop_value END) AS predictedCount,
    	        MIN(CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalMin,
    	        MAX(CASE WHEN source = 'experimental' THEN prop_value END) AS experimentalMax,
    	        MIN(CASE WHEN source = 'predicted' THEN prop_value END) AS predictedMin,
    	        MAX(CASE WHEN source = 'predicted' THEN prop_value END) AS predictedMax,
    	        prop_unit AS unit
    	    FROM combined
    	    GROUP BY prop_name, prop_unit
    	    """)
    	List<ChemicalPropertySummary> findSummaryByDtxsidAndPropName(@Param("dtxsid")String dtxsid, @Param("propName")String propName, @Param("propCategory")String propCategory);
    
    @NativeQuery("""
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
    		ORDER BY d.source_name ASC
    				""")
    List<ChemicalPropertySummaryExperimental> findExpermentalSummaryByDtxsidAndPropName(@Param("dtxsid")String dtxsid, @Param("propName")String propName, @Param("propCategory")String propCategory);
    
    @NativeQuery("""
    		SELECT
    		    pd.source_name AS sourceName,
    		    pd.source_description AS sourceDescription,
    		    pd.prop_value AS propValue,
    		    CASE
    		    	WHEN r.report_html IS NULL THEN NULL
			    	ELSE CAST('https://comptox.epa.gov/ctx-api/chemical/property/model/reports/html/search/?dtxsid=' || :dtxsid || '&modelId=' || CAST(pd.model_id AS VARCHAR) AS VARCHAR)
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
            ORDER BY pd.source_name ASC
    				""")
    List<ChemicalPropertySummaryPredicted> findPredictedSummaryByDtxsidAndPropName(@Param("dtxsid")String dtxsid, @Param("propName")String propName, @Param("propCategory")String propCategory);

}
