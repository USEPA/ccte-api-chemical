package gov.epa.ccte.api.chemical.projection.chemicallist;

import gov.epa.ccte.api.chemical.domain.ChemicalList;

import java.time.Instant;

/**
 * Projection for {@link ChemicalList}
 */
public interface ChemicalListWithDtxsids extends ChemicalListBase{
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

    String getDtxsids();
}