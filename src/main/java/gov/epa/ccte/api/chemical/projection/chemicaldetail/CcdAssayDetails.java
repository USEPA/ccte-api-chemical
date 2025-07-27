package gov.epa.ccte.api.chemical.projection.chemicaldetail;

public interface CcdAssayDetails extends CcdChemicalDetails {

	String getTop();

	String getScaledTop();

	String getAc50();

	Double getLogAc50();

	String getHitc();

}