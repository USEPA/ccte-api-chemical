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
	
	Void setPropName(String propName);
	Void setExperimentalAverage(Float experimentalAverage);
	Void setExperimentalCount(Integer experimentalCount);
	Void setExperimentalMedian(Float experimentalMedian);
	Void setExperimentalMin(Float experimentalMin);
	Void setExperimentalMax(Float experimentalMax);
	Void setPredictedAverage(Float predictedAverage);
	Void setPredictedCount(Integer predictedCount);
	Void setPredictedMedian(Float predictedMedian);
	Void setPredictedMin(Float predictedMin);
	Void setPredictedMax(Float predictedMax);
	Void setUnit(String unit);

}
