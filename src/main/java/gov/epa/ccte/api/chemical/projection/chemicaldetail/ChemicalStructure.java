package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "ChemicalStructure", description = "Attributes related to chemical structure for chemical details APIs")
public interface ChemicalStructure extends ChemicalDetailBase {
    String getId();

    String getCasrn();

    String getPreferredName();

    Integer getHasStructureImage();

    String getSmiles();

    String getInchiString();

    String getInchikey();

    String getQsarReadySmiles();

    String getMsReadySmiles();

    String getDtxsid();

    String getDtxcid();
}