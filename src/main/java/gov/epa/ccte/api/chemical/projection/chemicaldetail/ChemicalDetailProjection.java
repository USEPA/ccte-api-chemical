package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true,name = "ChemicalDetailProjection", description = "Projection options for chemical details APIs ")
public enum ChemicalDetailProjection {
    chemicaldetailstandard, chemicalidentifier, chemicalstructure, ntatoolkit, ccdchemicaldetails,chemicaldetailall
}
