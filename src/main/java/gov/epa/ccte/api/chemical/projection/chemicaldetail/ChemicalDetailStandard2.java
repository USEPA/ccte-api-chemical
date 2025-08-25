package gov.epa.ccte.api.chemical.projection.chemicaldetail;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail}
 */
public interface ChemicalDetailStandard2 {
    Long getId();

    String getDtxsid();

    String getCasrn();

    String getPreferredName();

    String getMolFormula();

    Double getMonoisotopicMass();

    Integer getQcLevel();

    String getQcLevelDesc();

    String getIupacName();

    String getSmiles();

    String getInchiString();

    Double getAverageMass();

    String getInchikey();
}