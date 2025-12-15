package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.CcdAssayDetails;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailStandard2;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.Compact;

import org.springframework.data.domain.Limit;
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
    <T>List<T> findByDtxsid(@Param("dtxsid") String dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxcid", path = "by-dtxcid", exported = true)
    <T>List<T> findByDtxcid(String dtxcid, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findBySmiles", path = "by-smiles", exported = true)
    List<Compact> findBySmiles(@Param("smiles")String smiles);
    
    // Query for SimilarSearchService
    @Query(value = "select smiles from ChemicalDetail where smiles is not null")
    String[] getAllSmiles();
    
    // Batch search
    @Transactional(readOnly = true)
    <T>List<T> findByDtxsidInOrderByDtxsidAsc(String[] dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    <T>List<T> findByDtxcidInOrderByDtxcidAsc(String[] dtxcid, Class<T> type);

    // Query for chemical files

    @Transactional(readOnly = true)
    @RestResource(path = "by-gsid",rel = "find-by-gsid",exported = true)
    @Query(value = "select c.molImage from ChemicalDetail c where c.genericSubstanceId = :gsid")
    byte[] getMolImageForGsid(@Param("gsid") String gsid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.molImage from ChemicalDetail c where c.dtxsid = :dtxsid")
    byte[] getMolImageForDtxsid(@Param("dtxsid") String dtxsid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.molImage from ChemicalDetail c where c.dtxcid = :dtxcid")
    byte[] getMolImageForDtxcid(@Param("dtxcid") String dtxcid);

    @Transactional(readOnly = true)
    @RestResource(path = "by-gsid",rel = "find-by-gsid",exported = true)
    @Query(value = "select c.molFile from ChemicalDetail c where c.genericSubstanceId = :gsid")
    Optional<String> getMolFileForGsid(@Param("gsid") String gsid);

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

    @Transactional(readOnly = true)
    @Query(value = "select c.inchikey from ChemicalDetail c where c.dtxsid = :dtxsid")
    Optional<String> getInchikeyForDtxsid(@Param("dtxsid") String dtxsid);

    @Transactional(readOnly = true)
    <T>List<T> findByIdGreaterThanAndDtxsidNotNull(Long id, Limit limit, Class<T> type);
    
    @Query(value = """
    	    SELECT
    	        -- CcdChemicalDetails fields
    	        cd.id AS id,
    	        cd.dtxsid AS dtxsid,
    	        cd.dtxcid AS dtxcid,
    	        cd.casrn AS casrn,
    	        cd.compound_id AS compoundId,
    	        cd.generic_substance_id AS genericSubstanceId,
    	        cd.preferred_name AS preferredName,
    	        cd.mol_formula AS molFormula,
    	        cd.monoisotopic_mass AS monoisotopicMass,
    	        cd.pubchem_cid AS pubchemCid,
    	        cd.smiles AS smiles,
    	        cd.inchi_string AS inchiString,
    	        cd.inchikey AS inchikey,
    	        cd.average_mass AS averageMass,
    	        cd.percent_assays AS percentAssays,
    	        cd.toxcast_select AS toxcastSelect,
    	        cd.qsar_ready_smiles AS qsarReadySmiles,
    	        cd.ms_ready_smiles AS msReadySmiles,
    	        cd.qc_level AS qcLevel,
    	        cd.qc_level_desc AS qcLevelDesc,
    	        cd.water_solubility_test AS waterSolubilityTest,
    	        cd.density AS density,
    	        cd.boiling_point_degc_test_pred AS boilingPointDegcTestPred,
    	        cd.melting_point_degc_test_pred AS meltingPointDegcTestPred,
    	        cd.octanol_water_partition AS octanolWaterPartition,
    	        cd.tetrahymena_pyriformis AS tetrahymenaPyriformis,
    	        cd.toxval_data AS toxvalData,
    	        cd.related_substance_count AS relatedSubstanceCount,
    	        cd.related_structure_count AS relatedStructureCount,
    	        cd.total_assays AS totalAssays,
    	        cd.active_assays AS activeAssays,
    	        cd.cpdata_count AS cpdataCount,
    	        cd.pubchem_count AS pubchemCount,
    	        cd.pubmed_count AS pubmedCount,
    	        cd.sources_count AS sourcesCount,
    	        cd.stereo AS stereo,
    	        cd.isotope AS isotope,
    	        cd.multicomponent AS multicomponent,
    	        cd.has_structure_image AS hasStructureImage,
    	        cd.iupac_name AS iupacName,
    	        cd.iris_link AS irisLink,
    	        cd.pprtv_link AS pprtvLink,
    	        cd.wikipedia_article AS wikipediaArticle,
    	        cd.expocat AS expocat,
    	        cd.expocat_median_prediction AS expocatMedianPrediction,
    	        cd.nhanes AS nhanes,
    	        cd.qc_notes AS qcNotes,
    	        cd.mol_file AS molFile,
    	        cd.mrv_file AS mrvFile,
    	        cd.descriptor_string_tsv AS descriptorStringTsv,
    	        cd.flash_point_degc_test_pred AS flashPointDegcTestPred,
    	        cd.devtox_test_pred AS devtoxTestPred,
    	        cd.viscosity_cp_cp_test_pred AS viscosityCpCpTestPred,
    	        cd.vapor_pressure_mmhg_test_pred AS vaporPressureMmhgTestPred,
    	        cd.vapor_pressure_mmhg_opera_pred AS vaporPressureMmhgOperaPred,
    	        cd.soil_adsorption_coefficient AS soilAdsorptionCoefficient,
    	        cd.biodegradation_half_life_days AS biodegradationHalfLifeDays,
    	        cd.bioconcentration_factor_test_pred AS bioconcentrationFactorTestPred,
    	        cd.bioconcentration_factor_opera_pred AS bioconcentrationFactorOperaPred,
    	        cd.atmospheric_hydroxylation_rate AS atmosphericHydroxylationRate,
    	        cd.ames_mutagenicity_test_pred AS amesMutagenicityTestPred,
    	        cd.pkaa_opera_pred AS pkaaOperaPred,
    	        cd.pkab_opera_pred AS pkabOperaPred,
    	        cd.logd5_5 AS logd55,
    	        cd.logd7_4 AS logd74,
    	        cd.ready_bio_deg AS readyBioDeg,
    	        cd.is_markush AS isMarkush,

    	        -- Assay fields
    	        bio.hitc AS hitc,
    	        bio_elem->>'top' AS top,
    	        bio_elem->>'top_over_cutoff' AS scaledTop,
    	        bio_elem->>'ac50' AS ac50,
    	        CASE
    	            WHEN bio_elem->>'ac50' IS NOT NULL
    	            THEN LOG(CAST(bio_elem->>'ac50' AS float))
    	        END AS logAc50

    	    FROM ch.v_chemical_details cd
    	    LEFT JOIN invitro.mv_bioactivity bio
    	        ON bio.dsstox_substance_id = cd.dtxsid,
    	         json_array_elements(json_build_array(bio.mc5_param)) AS bio_elem
    	    WHERE cd.dtxsid IN (:dtxsids)
    	    ORDER BY cd.dtxsid
    	""", nativeQuery = true)
    	List<CcdAssayDetails> getFullCcdAssayDetails(@Param("dtxsids") List<String> dtxsids);

}
