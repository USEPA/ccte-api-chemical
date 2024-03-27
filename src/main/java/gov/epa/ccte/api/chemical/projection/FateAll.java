package gov.epa.ccte.api.chemical.projection;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.Fate} entity
 */
public interface FateAll {
    Integer getId();

    String getDtxsid();

    String getDtxcid();

    String getEndpointName();

    Double getResultValue();

    String getUnit();

    Double getMaxValue();

    Double getMinValue();

    String getModelSource();

    String getDescription();

    String getValueType();
}