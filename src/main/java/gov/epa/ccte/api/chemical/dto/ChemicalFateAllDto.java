package gov.epa.ccte.api.chemical.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChemicalFateAllDto {
    private String propName;
    private JsonNode experimentalFateData;
    private JsonNode predictedFateData;

    public ChemicalFateAllDto(String propName, String experimentalFateData, String predictedFateData) {
        this.propName = propName;
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.experimentalFateData = mapper.readTree(experimentalFateData);
        } catch (Exception e) {
            this.experimentalFateData = null;
        }
        try {
            this.predictedFateData = mapper.readTree(predictedFateData);
        } catch (Exception e) {
            this.predictedFateData = null;
        }
    }


    public String getPropName() {
        return propName;
    }

    public JsonNode getExperimentalFateData() {
        return experimentalFateData;
    }

    public JsonNode getPredictedFateData() {
        return predictedFateData;
    }
}