package gov.epa.ccte.api.chemical.projection.search;

import java.time.Instant;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch}
 * This project is only to get all the data (mostly for modified_value) from database
 *
 */
public interface ChemicalSearchInternal {
	
    Integer getId();
    String getDtxsid();
    String getDtxcid();
    String getCasrn();
    String getSmiles();
    String getPreferredName();
    String getSearchGroup();
    String getSearchName();
    String getSearchValue();
    String getModifiedValue();
    Integer getRank();
    Integer getHasStructureImage();
    Boolean getIsMarkush();
    String getCreatedBy();
    Instant getCreatedAt();
    
    void setId(Integer id);
    void setDtxsid(String dtxsid);
    void setDtxcid(String dtxcid);
    void setCasrn(String casrn);
    void setSmiles(String smiles);
    void setPreferredName(String preferredName);
    void setSearchGroup(String searchGroup);
    void setSearchName(String searchName);
    void setSearchValue(String searchValue);
    void setModifiedValue(String modifiedValue);
    void setRank(Integer rank);
    void setHasStructureImage(Integer hasStructureImage);
    void setIsMarkush(Boolean isMarkush);
    void setCreatedBy(String createdBy);
    void setCreatedAt(Instant createdAt);
    
}