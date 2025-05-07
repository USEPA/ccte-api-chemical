package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertySummary;

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
    		CASE
    			WHEN AVG(d.prop_value) IS NULL THEN 'null'
    			ELSE CONCAT(AVG(d.prop_value), '(', COUNT(distinct d.prop_value), ')')
    		END AS experimentalAverage,
    		CASE
    			WHEN AVG(pd.prop_value) IS NULL THEN 'null'
    			ELSE CONCAT(AVG(pd.prop_value), '(', COUNT(distinct pd.prop_value), ')')
    		END AS predictedAverage,
    		CASE
    			WHEN MAX(d.prop_value) > MIN(d.prop_value) THEN CONCAT(CONCAT(MIN(d.prop_value), ' to '), MAX(d.prop_value))
    			ELSE CAST(MAX(d.prop_value) AS text)
    		END AS experimentalRange,
    		CASE
    			WHEN MAX(pd.prop_value) > MIN(pd.prop_value) THEN CONCAT(CONCAT(MIN(pd.prop_value), ' to '), MAX(pd.prop_value))
    			ELSE CAST(MAX(pd.prop_value) AS text)
    		END AS predictedRange,
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
    		CASE
    			WHEN AVG(d.prop_value) IS NULL THEN 'null'
    			ELSE CONCAT(AVG(d.prop_value), '(', COUNT(distinct d.prop_value), ')')
    		END AS experimentalAverage,
    		CASE
    			WHEN AVG(pd.prop_value) IS NULL THEN 'null'
    			ELSE CONCAT(AVG(pd.prop_value), '(', COUNT(distinct pd.prop_value), ')')
    		END AS predictedAverage,
    		CASE
    			WHEN MAX(d.prop_value) > MIN(d.prop_value) THEN CONCAT(CONCAT(MIN(d.prop_value), ' to '), MAX(d.prop_value))
    			ELSE CAST(MAX(d.prop_value) AS text)
    		END AS experimentalRange,
    		CASE
    			WHEN MAX(pd.prop_value) > MIN(pd.prop_value) THEN CONCAT(CONCAT(MIN(pd.prop_value), ' to '), MAX(pd.prop_value))
    			ELSE CAST(MAX(pd.prop_value) AS text)
    		END AS predictedRange,
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
}
