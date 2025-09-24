package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "NtaToolkit", description = "All attributes use for NTA Informatics Toolkit.")
public interface NtaToolkit extends ChemicalDetailBase {
	
    String getCasrn();
    String getPreferredName();
    Integer getActiveAssays();
    Long getCpdataCount();
    String getMolFormula();
    Double getMonoisotopicMass();
    Double getPercentAssays();
    Long getSourcesCount();
    Integer getTotalAssays();
    String getSmiles();
    String getInchikey();
    String getMsReadySmiles();
    String getDtxsid();
    String getDtxcid();
    String getExpocatMedianPrediction();
    String getExpocat();
    String getNhanes();
    
    
    Void setCasrn(String casrn);
    Void setPreferredName(String preferredName);
    Void setActiveAssays(Integer activeAssays);
    Void setCpDataCount(Long cpDataCount);
    Void setMolFormula(String molFormula);
    Void setMonoisotopicMass(Double monoisotopicMass);
    Void setPercentAssays(Double percentAssays);
    Void setSourcesCount(Long sourcesCount);
    Void setTotalAssays(Integer totalAssays);
    Void setSmiles(String smiles);
    Void setInchikey(String inchikey);
    Void setMsReadySmiles(String msReadySmiles);
    Void setDtxsid(String dtxsid);
    Void setDtxcid(String dtxcid);
    Void setExpocatMedianPrediction(String expocatMedianPrediction);
    Void setExpocat(String expocat);
    Void setNhanes(String nhanes);
    
}