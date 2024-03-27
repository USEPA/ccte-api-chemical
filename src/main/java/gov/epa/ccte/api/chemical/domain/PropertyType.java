package gov.epa.ccte.api.chemical.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum PropertyType {
    experimental, predicted
}
