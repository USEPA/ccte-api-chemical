package gov.epa.ccte.api.chemical.projection.chemicallist;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true,name = "ChemicalListProjection", description = "Projection options for chemical List APIs ")
public enum ChemicalListProjection {
    chemicallistall, chemicallistwithdtxsids, chemicallistname
}
