package gov.epa.ccte.api.chemical.projection.chemicallist;

import gov.epa.ccte.api.chemical.domain.ChemicalList;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Projection for {@link ChemicalList}
 */
@JsonPropertyOrder({
    "id",
	"listName",
	"label",
	"type",
	"shortDescription",
	"longDescription",
	"chemicalCount",
	"updatedAt",
	"dtxsids"
})
public interface ChemicalListWithDtxsids extends ChemicalListBase {
	
    Integer getId();
    String getListName();
    String getLabel();
    String getType();
    String getShortDescription();
    String getLongDescription();
    Long getChemicalCount();
    Instant getUpdatedAt();
    String getDtxsids();
    
    Void setid(Integer id);
    Void setListName(String listName);
    Void setLabel(String label);
    Void setType(String type);
    Void setShortDescription(String shortDescription);
    Void setLongDescription(String longDescription);
    Void setChemicalCount(Long chemicalCount);
    Void setUpdatedAt(Instant updatedAt);
    Void setDtxsids(String dtxsids);
    
}