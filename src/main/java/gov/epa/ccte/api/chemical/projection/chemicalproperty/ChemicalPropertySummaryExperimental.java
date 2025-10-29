package gov.epa.ccte.api.chemical.projection.chemicalproperty;

public interface ChemicalPropertySummaryExperimental {

	String getSourceName();
	String getSourceDescription();
	String getPublicSourceUrl();
	Double getPropValue();
	String getDirectUrl();
	String getAvailability();
	Boolean getShowLink();
	String getSpeciesLatin();
	String getResponseSite();
	
}
