package gov.epa.ccte.api.chemical.projection.search;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch} entity
 */
public interface ChemicalSearchAll {
	
    String getDtxsid();
    String getDtxcid();
    String getCasrn();
    String getSmiles();
    String getPreferredName();
    String getSearchName();
    String getSearchValue();
    Integer getRank();
    Integer getHasStructureImage();
    Boolean getIsMarkush();
    
    void setDtxsid(String dtxsid);
    void setDtxcid(String dtxcid);
    void setCasrn(String casrn);
    void setSmiles(String smiles);
    void setPreferredName(String preferredName);
    void setSearchName(String searchName);
    void setSearchValue(String searchValue);
    void setRank(Integer rank);
    void setHasStructureImage(Integer hasStructureImage);
    void setIsMarkush(Boolean isMarkush);
    
}