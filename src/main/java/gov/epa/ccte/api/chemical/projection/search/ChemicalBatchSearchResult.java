package gov.epa.ccte.api.chemical.projection.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch}
 */
@Value
public class ChemicalBatchSearchResult implements Serializable {
    @Size(max = 20)
    String dtxsid;
    @Size(max = 20)
    String dtxcid;
    @Size(max = 255)
    String casrn;
    String smiles;
    @Size(max = 255)
    String preferredName;
    @Size(max = 50)
    String searchName;
    String searchValue;
    Integer rank;
    Integer hasStructureImage;
    Boolean isMarkush;
    // add annotation for springdoc to add description for following
    @Schema(description = "Message about the search")
    List<String> searchMsgs;
    @Schema(description = "List of suggestions in case there is no match found")
    List<String> suggestions;
    Boolean isDuplicate;
}