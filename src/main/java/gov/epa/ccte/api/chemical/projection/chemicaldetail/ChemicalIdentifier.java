package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "ChemicalIdentifier", description = "Attributes related to chemical identification for chemical details APIs")
public interface ChemicalIdentifier extends ChemicalDetailBase {
    String getCasrn();

    String getPreferredName();

    String getIupacName();

    String getInchikey();

    String getDtxsid();

    String getDtxcid();
}