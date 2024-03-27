package gov.epa.ccte.api.chemical.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link gov.epa.ccte.api.chemical.domain.ChemicalProperty} entity
 */
@Data
public class ChemicalPropertyDto implements Serializable {
    @NotNull
    private final Integer id;
    @Size(max = 45)
    private final String dtxsid;
    @Size(max = 45)
    private final String dtxcid;
    private final String propType;
    @Size(max = 255)
    private final String unit;
    @Size(max = 255)
    private final String name;
    private final Double value;
    @Size(max = 255)
    private final String source;
    @Size(max = 1024)
    private final String description;
    @Size(max = 300)
    private String propertyId;
}