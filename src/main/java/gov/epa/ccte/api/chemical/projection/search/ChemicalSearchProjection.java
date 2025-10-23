package gov.epa.ccte.api.chemical.projection.search;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true,name = "ChemicalSearchProjection", description = "Projection options for chemical Search")
public enum ChemicalSearchProjection {
    chemicalsearchall, dtxsidonly
}
