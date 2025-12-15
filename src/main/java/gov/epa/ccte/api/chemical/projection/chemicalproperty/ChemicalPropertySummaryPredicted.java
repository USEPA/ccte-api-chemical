package gov.epa.ccte.api.chemical.projection.chemicalproperty;

public interface ChemicalPropertySummaryPredicted {
	
	String getSourceName();
	String getSourceDescription();
	Double getPropValue();
	String getLink();
	String getLinkAvailability();
	Boolean getShowLink();
	String getQmrfUrl();
	String getQmrfAvailability();
	Boolean getShowQmrf();

}
