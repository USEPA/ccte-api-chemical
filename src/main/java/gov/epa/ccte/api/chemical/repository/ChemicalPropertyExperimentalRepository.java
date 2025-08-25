package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyAll;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ChemicalPropertyExperimentalRepository extends JpaRepository<ChemicalPropertyExperimental, Long> {

    @Query(value = """
			SELECT 
				ex.id AS id,
				ex.dtxsid AS dtxsid,
				ex.dtxcid AS dtxcid,
				ex.smiles AS smiles,
				ex.prop_name AS propName,
				ex.dataset AS dataset,
				ex.prop_value AS propValue,
				ex.prop_unit AS propUnit,
				ex.prop_value_id AS propValueId,
				ex.prop_value_original AS propValueOriginal,
				ex.prop_value_text AS propValueText,
				ex.exp_details_temperature_c AS expDetailsTemperatureC,
				ex.exp_details_pressure_mmhg AS expDetailsPressureMmhg,
				ex.exp_details_ph AS expDetailsPh,
				ex.exp_details_response_site AS expDetailsResponseSite,
				ex.exp_details_species_latin AS expDetailsSpeciesLatin,
				ex.exp_details_species_common AS expDetailsSpeciesCommon,
				ex.exp_details_species_supercategory AS expDetailsSpeciesSupercategory,
				ex.source_name AS sourceName,
				ex.source_description AS SourceDescription,
				ex.public_source_name AS publicSourceName,
				ex.public_source_description AS publicSourceDescription,
				ex.public_source_url AS publicSourceUrl,
				ex.direct_url AS directUrl,
				ex.ls_name AS lsName,
				ex.ls_citation AS lsCitation,
				ex.ls_doi AS lsDoi,
				ex.brief_citation AS briefCitation,
				ex.public_source_original_name AS publicSourceOriginalName,
				ex.public_source_original_description AS publicSourceOriginalDescription,
				ex.public_source_original_url AS publicSourceOriginalUrl
			FROM chemprop.mv_experimental_data ex
			WHERE ex.dtxsid = :dtxsid AND ex.prop_category = 'Physchem'
			""", nativeQuery = true)
    List<ChemicalPropertyAll> findExperimentalByDtxsid(String dtxsid);

    
    @Query(value = """
			SELECT 
				ex.id AS id,
				ex.dtxsid AS dtxsid,
				ex.dtxcid AS dtxcid,
				ex.smiles AS smiles,
				ex.prop_name AS propName,
				ex.dataset AS dataset,
				ex.prop_value AS propValue,
				ex.prop_unit AS propUnit,
				ex.prop_value_id AS propValueId,
				ex.prop_value_original AS propValueOriginal,
				ex.prop_value_text AS propValueText,
				ex.exp_details_temperature_c AS expDetailsTemperatureC,
				ex.exp_details_pressure_mmhg AS expDetailsPressureMmhg,
				ex.exp_details_ph AS expDetailsPh,
				ex.exp_details_response_site AS expDetailsResponseSite,
				ex.exp_details_species_latin AS expDetailsSpeciesLatin,
				ex.exp_details_species_common AS expDetailsSpeciesCommon,
				ex.exp_details_species_supercategory AS expDetailsSpeciesSupercategory,
				ex.source_name AS sourceName,
				ex.source_description AS SourceDescription,
				ex.public_source_name AS publicSourceName,
				ex.public_source_description AS publicSourceDescription,
				ex.public_source_url AS publicSourceUrl,
				ex.direct_url AS directUrl,
				ex.ls_name AS lsName,
				ex.ls_citation AS lsCitation,
				ex.ls_doi AS lsDoi,
				ex.brief_citation AS briefCitation,
				ex.public_source_original_name AS publicSourceOriginalName,
				ex.public_source_original_description AS publicSourceOriginalDescription,
				ex.public_source_original_url AS publicSourceOriginalUrl
			FROM chemprop.mv_experimental_data ex
			WHERE ex.dtxsid in :dtxsids AND ex.prop_category = 'Physchem'
			""", nativeQuery = true)
    List<ChemicalPropertyAll> findExperimentalByDtxsidInOrderByDtxsidAsc(String[] dtxsids);

    
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
			SELECT 
				ex.id AS id,
				ex.dtxsid AS dtxsid,
				ex.dtxcid AS dtxcid,
				ex.smiles AS smiles,
				ex.prop_name AS propName,
				ex.dataset AS dataset,
				ex.prop_value AS propValue,
				ex.prop_unit AS propUnit,
				ex.prop_value_id AS propValueId,
				ex.prop_value_original AS propValueOriginal,
				ex.prop_value_text AS propValueText,
				ex.exp_details_temperature_c AS expDetailsTemperatureC,
				ex.exp_details_pressure_mmhg AS expDetailsPressureMmhg,
				ex.exp_details_ph AS expDetailsPh,
				ex.exp_details_response_site AS expDetailsResponseSite,
				ex.exp_details_species_latin AS expDetailsSpeciesLatin,
				ex.exp_details_species_common AS expDetailsSpeciesCommon,
				ex.exp_details_species_supercategory AS expDetailsSpeciesSupercategory,
				ex.source_name AS sourceName,
				ex.source_description AS SourceDescription,
				ex.public_source_name AS publicSourceName,
				ex.public_source_description AS publicSourceDescription,
				ex.public_source_url AS publicSourceUrl,
				ex.direct_url AS directUrl,
				ex.ls_name AS lsName,
				ex.ls_citation AS lsCitation,
				ex.ls_doi AS lsDoi,
				ex.brief_citation AS briefCitation,
				ex.public_source_original_name AS publicSourceOriginalName,
				ex.public_source_original_description AS publicSourceOriginalDescription,
				ex.public_source_original_url AS publicSourceOriginalUrl
			FROM chemprop.mv_experimental_data ex
			WHERE ex.dtxsid = :dtxsid AND ex.prop_category = 'Env. Fate/transport'
			""", nativeQuery = true)
    List<ChemicalPropertyAll> findFateByDtxsid(String dtxsid);
    
    
    @Query(value = """
			SELECT 
				ex.id AS id,
				ex.dtxsid AS dtxsid,
				ex.dtxcid AS dtxcid,
				ex.smiles AS smiles,
				ex.prop_name AS propName,
				ex.dataset AS dataset,
				ex.prop_value AS propValue,
				ex.prop_unit AS propUnit,
				ex.prop_value_id AS propValueId,
				ex.prop_value_original AS propValueOriginal,
				ex.prop_value_text AS propValueText,
				ex.exp_details_temperature_c AS expDetailsTemperatureC,
				ex.exp_details_pressure_mmhg AS expDetailsPressureMmhg,
				ex.exp_details_ph AS expDetailsPh,
				ex.exp_details_response_site AS expDetailsResponseSite,
				ex.exp_details_species_latin AS expDetailsSpeciesLatin,
				ex.exp_details_species_common AS expDetailsSpeciesCommon,
				ex.exp_details_species_supercategory AS expDetailsSpeciesSupercategory,
				ex.source_name AS sourceName,
				ex.source_description AS SourceDescription,
				ex.public_source_name AS publicSourceName,
				ex.public_source_description AS publicSourceDescription,
				ex.public_source_url AS publicSourceUrl,
				ex.direct_url AS directUrl,
				ex.ls_name AS lsName,
				ex.ls_citation AS lsCitation,
				ex.ls_doi AS lsDoi,
				ex.brief_citation AS briefCitation,
				ex.public_source_original_name AS publicSourceOriginalName,
				ex.public_source_original_description AS publicSourceOriginalDescription,
				ex.public_source_original_url AS publicSourceOriginalUrl
			FROM chemprop.mv_experimental_data ex
			WHERE ex.dtxsid in :dtxsids AND ex.prop_category = 'Env. Fate/transport'
			""", nativeQuery = true)
    List<ChemicalPropertyAll> findFateByDtxsidInOrderByDtxsidAsc(String[] dtxsids);
    
}
