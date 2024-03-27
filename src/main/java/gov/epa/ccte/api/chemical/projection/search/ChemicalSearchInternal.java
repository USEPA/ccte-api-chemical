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
}