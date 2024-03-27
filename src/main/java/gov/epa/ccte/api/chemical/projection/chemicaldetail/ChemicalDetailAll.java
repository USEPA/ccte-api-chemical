package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "chemicaldetailall", description = "All attributes available for chemical details APIs")
public interface ChemicalDetailAll extends ChemicalDetailBase {
    String getId();

    String getCasrn();

    Integer getCompoundId();

    Integer getGenericSubstanceId();

    String getPreferredName();

    Integer getActiveAssays();

    Long getCpdataCount();

    String getMolFormula();

    Double getMonoisotopicMass();

    Double getPercentAssays();

    Integer getPubchemCount();

    Double getPubmedCount();

    Long getSourcesCount();

    Integer getQcLevel();

    String getQcLevelDesc();

    String getStereo();

    Integer getIsotope();

    Integer getMulticomponent();

    Integer getTotalAssays();

    Integer getPubchemCid();

    Long getRelatedSubstanceCount();

    Long getRelatedStructureCount();

    Integer getHasStructureImage();

    String getIupacName();

    String getSmiles();

    String getInchiString();

    Double getAverageMass();

    String getInchikey();

    String getQcNotes();

    String getQsarReadySmiles();

    String getMsReadySmiles();

    String getIrisLink();

    String getPprtvLink();

    String getWikipediaArticle();

    String getDescriptorStringTsv();

    Boolean getIsMarkush();

    String getDtxsid();

    String getDtxcid();

    String getToxcastSelect();

    String getExpocatMedianPrediction();

    String getExpocat();

    String getNhanes();

    String getToxvalData();

    Double getWaterSolubilityTest();

    Double getWaterSolubilityOpera();

    Double getViscosityCpCpTestPred();

    Double getVaporPressureMmhgTestPred();

    Double getVaporPressureMmhgOperaPred();

    Double getThermalConductivity();

    Double getTetrahymenaPyriformis();

    Double getSurfaceTension();

    Double getSoilAdsorptionCoefficient();

    Double getOralRatLd50Mol();

    Double getOperaKmDaysOperaPred();

    Double getOctanolWaterPartition();

    Double getOctanolAirPartitionCoeff();

    Double getMeltingPointDegcTestPred();

    Double getMeltingPointDegcOperaPred();

    Double getHrFatheadMinnow();

    Double getHrDiphniaLc50();

    Double getHenrysLawAtm();

    Double getFlashPointDegcTestPred();

    Double getDevtoxTestPred();

    Double getDensity();

    Double getBoilingPointDegcTestPred();

    Double getBoilingPointDegcOperaPred();

    Double getBiodegradationHalfLifeDays();

    Double getBioconcentrationFactorTestPred();

    Double getBioconcentrationFactorOperaPred();

    Double getAtmosphericHydroxylationRate();

    Double getAmesMutagenicityTestPred();

    Double getPkaaOperaPred();

    Double getPkabOperaPred();

}