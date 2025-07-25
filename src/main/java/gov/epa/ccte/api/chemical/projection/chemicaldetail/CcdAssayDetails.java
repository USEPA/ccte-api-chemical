package gov.epa.ccte.api.chemical.projection.chemicaldetail;

public interface CcdAssayDetails extends CcdChemicalDetails{
	

	default String getHitc() {
		if (getActiveAssays() != null && getActiveAssays() > 0) {
			return "active";
		} else {
			return "inactive";
		}
	}

}