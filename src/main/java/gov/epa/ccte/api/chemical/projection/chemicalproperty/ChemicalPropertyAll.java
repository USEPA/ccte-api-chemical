package gov.epa.ccte.api.chemical.projection.chemicalproperty;

//for experimental and fate endpoints
public interface ChemicalPropertyAll {

	Long getId();
	String getDtxsid();
	String getDtxcid();
	String getSmiles();
	String getPropName();
	String getDataset();
	Double getPropValue();
	String getPropUnit();
	Long getPropValueId();
	String getPropValueOriginal();
	String getPropValueText();
	Long getExpDetailsTemperatureC();
	Long getExpDetailsPressureMmhg();
	Long getExpDetailsPh();
	String getExpDetailsResponseSite();
	String getExpDetailsSpeciesLatin();
	String getExpDetailsSpeciesCommon();
	String getExpDetailsSpeciesSupercategory();
	String getSourceName();
	String getSourceDescription();
	String getPublicSourceName();
	String getPublicSourceDescription();
	String getPublicSourceUrl();
	String getDirectUrl();
	String getLsName();
	String getLsCitation();
	String getLsDoi();
	String getBriefCitation();
	String getPublicSourceOriginalName();
	String getPublicSourceOriginalDescription();
	String getPublicSourceOriginalUrl();
}
