package gov.epa.ccte.api.chemical.projection.chemicaldetail;

public interface CcdAssayDetails extends CcdChemicalDetails {

	Double getTop();

	Double getScaledTop();

	Double getAc50();

	Double getLogAc50();

	Integer getHitc();

}