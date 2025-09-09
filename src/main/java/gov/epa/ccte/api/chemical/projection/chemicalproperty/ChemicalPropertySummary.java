package gov.epa.ccte.api.chemical.projection.chemicalproperty;

public interface ChemicalPropertySummary {

	String getPropName();
	Float getExperimentalAverage();
	Integer getExperimentalCount();
	Float getExperimentalMedian();
	Float getExperimentalMin();
	Float getExperimentalMax();
	Float getPredictedAverage();  
	Integer getPredictedCount();
	Float getPredictedMedian();
	Float getPredictedMin();
	Float getPredictedMax();
	String getUnit();

}
