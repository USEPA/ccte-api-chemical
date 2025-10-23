package gov.epa.ccte.api.chemical.projection.chemicaldetail;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail}
 */
public interface ChemicalDetailStandard2 {
	
    Long getId();
    String getDtxsid();
    String getCasrn();
    String getPreferredName();
    String getMolFormula();
    Double getMonoisotopicMass();
    Integer getQcLevel();
    String getQcLevelDesc();
    String getIupacName();
    String getSmiles();
    String getInchiString();
    Double getAverageMass();
    String getInchikey();
    
    
    Void setId(Long id);
    Void setDtxsid(String dtxsid);
    Void setCasrn(String casrn);
    Void setPreferredName(String preferredName);
    Void setMolFormula(String molFormula);
    Void setMonoisotopicMass(Double monoisotopicMass);
    Void setQcLevel(Integer qcLevel);
    Void setQcLevelDesc(String qcLevelDesc);
    Void setIupacName(String iupacName);
    Void setSmiles(String smiles);
    Void setInchiString(String inchiString);
    Void setAverageMass(Double averageMass);
    Void setInchikey(String inchikey);
    
}