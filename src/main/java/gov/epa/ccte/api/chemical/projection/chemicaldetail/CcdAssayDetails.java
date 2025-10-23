package gov.epa.ccte.api.chemical.projection.chemicaldetail;

public interface CcdAssayDetails extends CcdChemicalDetails {

	Double getTop();
	Double getScaledTop();
	Double getAc50();
	Double getLogAc50();
	Integer getHitc();
	
	
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
    Void setPubChemCount(Integer pubchemCount);
    Void setPubMedCount(Double pubmedCount);
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
	Void setTop(Double top);
	Void setScaledTop(Double scaledTop);
	Void setAc50(Double ac50);
	Void setLogAc50(Double logAc50);
	Void setHitc(Integer hitc);
	
}