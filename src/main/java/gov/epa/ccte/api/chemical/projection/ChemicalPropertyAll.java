package gov.epa.ccte.api.chemical.projection;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalProperty} entity
 */
public interface ChemicalPropertyAll {
    Integer getId();

    String getDtxsid();

    String getDtxcid();

    String getPropType();

    String getUnit();

    String getName();

    Double getValue();

    String getSource();

    String getDescription();

    String getPropertyId();
}