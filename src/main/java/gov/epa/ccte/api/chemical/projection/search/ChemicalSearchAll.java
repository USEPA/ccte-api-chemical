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
}