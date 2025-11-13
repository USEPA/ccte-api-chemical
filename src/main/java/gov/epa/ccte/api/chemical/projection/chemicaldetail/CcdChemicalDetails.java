package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "ccdchemicaldetails", description = "Standard attributes available for chemical details APIs")
public interface CcdChemicalDetails extends ChemicalDetailBase {
	
    Long getId();
    String getCasrn();
    Integer getCompoundId();
    Integer getGenericSubstanceId();
    String getPreferredName();
    Integer getActiveAssays();
    Long getCpdataCount();
    String getMolFormula();
    Double getMonoisotopicMass();
    Double getAverageMass();
    Double getPercentAssays();
    Integer getPubchemCount();
    Double getPubmedCount();
    String getStereo();
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
    Boolean getIsMarkush();
    String getDtxsid();
    String getDtxcid();
    String getToxcastSelect();
    
    Void setId(Long id);
    Void setCasrn(String casrn);
    Void setCompoundId(Integer compoundId);
    Void setGenericSubstanceId(Integer gsid);
    Void setPreferredName(String preferredName);
    Void setActiveAssays(Integer activeAssays);
    Void setCpdataCount(Long cpdataCount);
    Void setMolFormula(String molFormula);
    Void setMonoisotopicMass(Double monoisotopicMass);
    Void setAverageMass(Double averageMass);
    Void setPercentAssays(Double percentAssays);
    Void setPubchemCount(Integer pubchemCount);
    Void setPubmedCount(Double pubmedCount);
    Void setStereo(String stereo);
    Void setSourcesCount(Long sourcesCount);
    Void setQcLevel(Integer qcLevel);
    Void setQcLevelDesc(String qcLevelDesc);
    Void setIsotope(Integer isotope);
    Void setMulticomponent(Integer multicomponent);
    Void setTotalAssays(Integer totalAssays);
    Void setPubchemCid(Integer pubchemCid);
    Void setRelatedSubstanceCount(Long relatedSubstanceCount);
    Void setRelatedStructureCount(Long relatedStructureCount);
    Void setHasStructureImage(Integer hasStructureImage);
    Void setIupacName(String iupacName);
    Void setSmiles(String smiles);
    Void setInchiString(String inchiString);
    Void setInchikey(String inchikey);
    Void setQcNotes(String qcNotes);
    Void setQsarReadySmiles(String qsarReadySmiles);
    Void setMsReadySmiles(String msReadySmiles);
    Void setIrisLink(String irisLink);
    Void setPprtvLink(String pprtvLink);
    Void setWikipediaArticle(String wikipediaArticle);
    Void setIsMarkush(Boolean isMarkush);
    Void setDtxsid(String dtxsid);
    Void setDtxcid(String dtxcid);
    Void setToxcastSelect(String toxcastSelect);
    
}