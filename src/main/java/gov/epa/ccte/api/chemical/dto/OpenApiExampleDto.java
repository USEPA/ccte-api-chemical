package gov.epa.ccte.api.chemical.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiExampleDto {
    private String description;
    private String summary;
    private String identifier;

    private String results;
}
