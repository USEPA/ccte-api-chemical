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
	
	Void setId(Long id);
	Void setDtxsid(String dtxsid);
	Void setDtxcid(String dtxcid);
	Void setSmiles(String smiles);
	Void setPropName(String propName);
	Void setDataset(String dataset);
	Void setPropValue(Double propValue);
	Void setPropUnit(String propUnit);
	Void setPropValueId(Long propValueId);
	Void setPropValueOriginal(String propValueOriginal);
	Void setPropValueText(String propValueText);
	Void setExpDetailsTemperatureC(Long expDetailsTemperatureC);
	Void setExpDetailsPressureMmhg(Long expDetailsPressureMmhg);
	Void setExpDetailsPh(Long expDetailsPh);
	Void setExpDetailsResponseSite(String expDetailsResponseSite);
	Void setExpDetailsSpeciesLatin(String expDetailsSpeciesLatin);
	Void setExpDetailsSpeciesCommon(String expDetailsSpeciesCommon);
	Void setExpDetailsSpeciesSupercategory(String expDetailsSpeciesSupercategory);
	Void setSourceName(String sourceName);
	Void setSourceDescription(String sourceDescription);
	Void setPublicSourceName(String publicSourceName);
	Void setPublicSourceDescription(String publicSourceDescription);
	Void setPublicSourceUrl(String publicSourceUrl);
	Void setDirectUrl(String directUrl);
	Void setLsName(String lsName);
	Void setLsCitation(String lsCitation);
	Void setLsDoi(String lsDoi);
	Void setBriefCitation(String briefCitation);
	Void setPublicSourceOriginalName(String publicSourceOriginalName);
	Void setPublicSourceOriginalDescription(String publicSourceOriginalDescription);
	Void setPublicSourceOriginalUrl(String publicSourceOriginalUrl);
	
}
