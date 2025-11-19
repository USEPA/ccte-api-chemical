package gov.epa.ccte.api.chemical.dto;

import java.util.List;

public class ChemicalFateBatchDto {
    private String dtxsid;
    private List<PropertyDto> properties;

    public ChemicalFateBatchDto(String dtxsid, List<PropertyDto> properties) {
        this.dtxsid = dtxsid;
        this.properties = properties;
    }

    public String getDtxsid() {
        return dtxsid;
    }

    public List<PropertyDto> getProperties() {
        return properties;
    }

    public static class PropertyDto {
        private String propName;
        private Object experimentalFateData;
        private Object predictedFateData;

        public PropertyDto() {
            // Default constructor for Jackson
        }

        public PropertyDto(String propName, Object experimentalFateData, Object predictedFateData) {
            this.propName = propName;
            this.experimentalFateData = experimentalFateData;
            this.predictedFateData = predictedFateData;
        }

        public String getPropName() {
            return propName;
        }

        public Object getExperimentalFateData() {
            return experimentalFateData;
        }

        public Object getPredictedFateData() {
            return predictedFateData;
        }
    }
}