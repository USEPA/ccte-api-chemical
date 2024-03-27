package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "v_chemical_details", schema = "ch")
public class ChemicalDetail {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "dtxsid", length = Integer.MAX_VALUE)
    private String dtxsid;

    @Column(name = "dtxcid", length = Integer.MAX_VALUE)
    private String dtxcid;

    @Size(max = 255)
    @Column(name = "casrn")
    private String casrn;

    @Column(name = "compound_id")
    private Integer compoundId;

    @Column(name = "generic_substance_id")
    private Integer genericSubstanceId;

    @Size(max = 255)
    @Column(name = "preferred_name")
    private String preferredName;

    @Column(name = "active_assays")
    private Integer activeAssays;

    @Column(name = "cpdata_count")
    private Long cpdataCount;

    @Size(max = 255)
    @Column(name = "mol_formula")
    private String molFormula;

    @Column(name = "monoisotopic_mass")
    private Double monoisotopicMass;

    @Column(name = "percent_assays")
    private BigDecimal percentAssays;

    @Column(name = "pubchem_count")
    private Integer pubchemCount;

    @Column(name = "pubmed_count")
    private Double pubmedCount;

    @Column(name = "sources_count")
    private Long sourcesCount;

    @Column(name = "qc_level")
    private Integer qcLevel;

    @Size(max = 255)
    @Column(name = "qc_level_desc")
    private String qcLevelDesc;

    @Column(name = "stereo", length = Integer.MAX_VALUE)
    private String stereo;

    @Column(name = "isotope")
    private Integer isotope;

    @Column(name = "multicomponent")
    private Integer multicomponent;

    @Column(name = "total_assays")
    private Integer totalAssays;

    @Column(name = "toxcast_select", length = Integer.MAX_VALUE)
    private String toxcastSelect;

    @Column(name = "pubchem_cid")
    private Integer pubchemCid;

    @Column(name = "mol_file", length = Integer.MAX_VALUE)
    private String molFile;

    @Column(name = "mrv_file", length = Integer.MAX_VALUE)
    private String mrvFile;

    @Column(name = "related_substance_count")
    private Long relatedSubstanceCount;

    @Column(name = "related_structure_count")
    private Long relatedStructureCount;

    @Column(name = "mol_image")
    private byte[] molImage;

    @Column(name = "has_structure_image")
    private Integer hasStructureImage;

    @Size(max = 5000)
    @Column(name = "iupac_name", length = 5000)
    private String iupacName;

    @Column(name = "smiles", length = Integer.MAX_VALUE)
    private String smiles;

    @Column(name = "inchi_string", length = Integer.MAX_VALUE)
    private String inchiString;

    @Column(name = "average_mass")
    private Double averageMass;

    @Size(max = 255)
    @Column(name = "inchikey")
    private String inchikey;

    @Size(max = 4000)
    @Column(name = "qc_notes", length = 4000)
    private String qcNotes;

    @Column(name = "qsar_ready_smiles", length = Integer.MAX_VALUE)
    private String qsarReadySmiles;

    @Column(name = "ms_ready_smiles", length = Integer.MAX_VALUE)
    private String msReadySmiles;

    @Size(max = 255)
    @Column(name = "iris_link")
    private String irisLink;

    @Size(max = 255)
    @Column(name = "pprtv_link")
    private String pprtvLink;

    @Size(max = 255)
    @Column(name = "wikipedia_article")
    private String wikipediaArticle;

    @Size(max = 50)
    @Column(name = "expocat_median_prediction", length = 50)
    private String expocatMedianPrediction;

    @Size(max = 5)
    @Column(name = "expocat", length = 5)
    private String expocat;

    @Size(max = 5)
    @Column(name = "nhanes", length = 5)
    private String nhanes;

    @Size(max = 5)
    @Column(name = "toxval_data", length = 5)
    private String toxvalData;

    @Column(name = "water_solubility_test")
    private Double waterSolubilityTest;

    @Column(name = "water_solubility_opera")
    private Double waterSolubilityOpera;

    @Column(name = "viscosity_cp_cp_test_pred")
    private Double viscosityCpCpTestPred;

    @Column(name = "vapor_pressure_mmhg_test_pred")
    private Double vaporPressureMmhgTestPred;

    @Column(name = "vapor_pressure_mmhg_opera_pred")
    private Double vaporPressureMmhgOperaPred;

    @Column(name = "thermal_conductivity")
    private Double thermalConductivity;

    @Column(name = "tetrahymena_pyriformis")
    private Double tetrahymenaPyriformis;

    @Column(name = "surface_tension")
    private Double surfaceTension;

    @Column(name = "soil_adsorption_coefficient")
    private Double soilAdsorptionCoefficient;

    @Column(name = "oral_rat_ld50_mol")
    private Double oralRatLd50Mol;

    @Column(name = "opera_km_days_opera_pred")
    private Double operaKmDaysOperaPred;

    @Column(name = "octanol_water_partition")
    private Double octanolWaterPartition;

    @Column(name = "octanol_air_partition_coeff")
    private Double octanolAirPartitionCoeff;

    @Column(name = "melting_point_degc_test_pred")
    private Double meltingPointDegcTestPred;

    @Column(name = "melting_point_degc_opera_pred")
    private Double meltingPointDegcOperaPred;

    @Column(name = "hr_fathead_minnow")
    private Double hrFatheadMinnow;

    @Column(name = "hr_diphnia_lc50")
    private Double hrDiphniaLc50;

    @Column(name = "henrys_law_atm")
    private Double henrysLawAtm;

    @Column(name = "flash_point_degc_test_pred")
    private Double flashPointDegcTestPred;

    @Column(name = "devtox_test_pred")
    private Double devtoxTestPred;

    @Column(name = "density")
    private Double density;

    @Column(name = "boiling_point_degc_test_pred")
    private Double boilingPointDegcTestPred;

    @Column(name = "boiling_point_degc_opera_pred")
    private Double boilingPointDegcOperaPred;

    @Column(name = "biodegradation_half_life_days")
    private Double biodegradationHalfLifeDays;

    @Column(name = "bioconcentration_factor_test_pred")
    private Double bioconcentrationFactorTestPred;

    @Column(name = "bioconcentration_factor_opera_pred")
    private Double bioconcentrationFactorOperaPred;

    @Column(name = "atmospheric_hydroxylation_rate")
    private Double atmosphericHydroxylationRate;

    @Column(name = "ames_mutagenicity_test_pred")
    private Double amesMutagenicityTestPred;

    @Column(name = "pkaa_opera_pred")
    private Double pkaaOperaPred;

    @Column(name = "pkab_opera_pred")
    private Double pkabOperaPred;

    @Size(max = 20000)
    @Column(name = "descriptor_string_tsv", length = 20000)
    private String descriptorStringTsv;

    @Column(name = "logd5_5")
    private Double logd55;

    @Column(name = "logd7_4")
    private Double logd74;

    @Column(name = "ready_bio_deg")
    private Double readyBioDeg;

    @Column(name = "is_markush")
    private Boolean isMarkush;
}