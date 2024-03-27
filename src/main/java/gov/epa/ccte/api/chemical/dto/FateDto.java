package gov.epa.ccte.api.chemical.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link gov.epa.ccte.api.chemical.domain.Fate} entity
 */
@Data
public class FateDto implements Serializable {
    @NotNull
    private final Integer id;
    @Size(max = 45)
    private final String dtxsid;
    @Size(max = 45)
    private final String dtxcid;
    @Size(max = 255)
    private final String endpointName;
    private final Double resultValue;
    @Size(max = 255)
    private final String unit;
    private final Double maxValue;
    private final Double minValue;
    @Size(max = 255)
    private final String modelSource;
    @Size(max = 1024)
    private final String description;
    private final String valueType;
}