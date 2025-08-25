package gov.epa.ccte.api.chemical.projection.chemicalproperty;

public interface ChemicalPropertySummary {

	String getPropName();
	String getExperimentalAverage();
	Float getExperimentalMedian();
	String getExperimentalRange();    
	String getPredictedAverage();     
	Float getPredictedMedian();
	String getPredictedRange();
	String getUnit();

}
