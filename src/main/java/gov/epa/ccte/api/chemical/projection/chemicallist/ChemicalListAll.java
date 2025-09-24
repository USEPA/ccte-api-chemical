package gov.epa.ccte.api.chemical.projection.chemicallist;

import java.time.Instant;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalList}
 */
public interface ChemicalListAll extends ChemicalListBase{
	
    Integer getId();
    String getListName();
    String getLabel();
    String getType();
    String getVisibility();
    String getShortDescription();
    String getLongDescription();
    Long getChemicalCount();
    Instant getCreatedAt();
    Instant getUpdatedAt();
    
    Void setid(Integer id);
	Void setListName(String listName);
	Void setLabel(String label);
	Void setType(String type);
	Void setVisibility(String visibility);
	Void setShortDescription(String shortDescription);
	Void setLongDescription(String longDescription);
	Void setChemicalCount(Long chemicalCount);
	Void setCreatedAt(Instant createdAt);
	Void setUpdatedAt(Instant updatedAt);

}