package gov.epa.ccte.api.chemical.projection.chemicaldetail;


public interface ChemicalDetailAllIds {
	
	Long getId();
	String getDtxsid();
	String getDtxcid();

	
	Void setId(Long id);
	Void setDtxsid(String dtxsid);
	Void setDtxcid(String dtxcid);
	
}