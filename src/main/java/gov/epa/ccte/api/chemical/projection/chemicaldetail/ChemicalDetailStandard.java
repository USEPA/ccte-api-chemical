package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "ChemicalDetailStandard", description = "Standard attributes available for chemical details APIs")
public interface ChemicalDetailStandard extends ChemicalDetailBase {
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

}