package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail}
 */
@Schema(name = "compact", description = "Minimum attributes for chemical details APIs")
public interface Compact extends ChemicalDetailBase {
    String getDtxsid();

    String getCasrn();

    String getPreferredName();
}