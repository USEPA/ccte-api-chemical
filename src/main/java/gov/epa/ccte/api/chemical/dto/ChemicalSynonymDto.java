package gov.epa.ccte.api.chemical.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.epa.ccte.api.chemical.domain.ChemicalSynonym;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ChemicalSynonym} entity
 */

@Data
public class ChemicalSynonymDto implements Serializable {
    @Size(max = 45)
    private final String dtxsid;
    private final String pcCode;
    @JsonIgnore
    private final String synonyms;

    @JsonProperty
    String[] getSynonymsName(){
        return synonyms.split("\\|");
    }
}