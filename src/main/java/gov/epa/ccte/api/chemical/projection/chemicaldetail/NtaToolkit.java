package gov.epa.ccte.api.chemical.projection.chemicaldetail;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Schema(name = "NtaToolkit", description = "All attributes use for NTA Informatics Toolkit.")
public interface NtaToolkit extends ChemicalDetailBase {
    String getCasrn();

    String getPreferredName();

    Integer getActiveAssays();

    Long getCpdataCount();

    String getMolFormula();

    Double getMonoisotopicMass();

    Double getPercentAssays();

    Long getSourcesCount();

    Integer getTotalAssays();

    String getSmiles();

    String getInchikey();

    String getMsReadySmiles();

    String getDtxsid();

    String getDtxcid();

    String getExpocatMedianPrediction();

    String getExpocat();

    String getNhanes();
}