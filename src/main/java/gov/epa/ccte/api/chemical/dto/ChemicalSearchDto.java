package gov.epa.ccte.api.chemical.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch} entity
 */
@Data
public class ChemicalSearchDto implements Serializable {
    @NotNull
    private final Integer id;
    @Size(max = 20)
    private final String dtxsid;
    @Size(max = 20)
    private final String dtxcid;
    @Size(max = 255)
    private final String casrn;
    private final String smiles;
    @Size(max = 255)
    private final String preferredName;
    @Size(max = 50)
    private final String searchName;
    private final String searchValue;
    private final Integer rank;
    private final Integer hasStructureImage;
    private final Boolean isMarkush;
}