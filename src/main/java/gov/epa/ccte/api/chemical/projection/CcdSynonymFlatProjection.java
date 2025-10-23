package gov.epa.ccte.api.chemical.projection;

public interface CcdSynonymFlatProjection {
	
    String getSynonym();
    String getQuality();
    
    Void setSynonym(String synonym);
    Void setQuality(String quality);
    
}
