package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ChemicalPropertyResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.dto.ChemicalFateAllDto;
import gov.epa.ccte.api.chemical.dto.ChemicalFateBatchDto;
import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.*;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyExperimentalRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyPredictedRepository;


import java.time.LocalDateTime;
import java.util.*;

@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.WARN)
@WebMvcTest(ChemicalPropertyResource.class)
@ExtendWith(MockitoExtension.class)
public class ChemicalPropertyResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalPropertyExperimentalRepository experimentalRepository;
    @MockitoBean
    private ChemicalPropertyPredictedRepository predictedRepository;

    private ChemicalPropertyPredicted predictedProperty;
    private ChemicalPropertyExperimental propertyExperimental;
    private ChemicalFateAllDto propertyFate;
    private ChemicalPropertyNames propertyNames;
    private ChemicalPropertySummary propertySummary, fateSummary;
    private ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    @BeforeEach
    void setUp() {

        predictedProperty = ChemicalPropertyPredicted.builder()
                .id(34861738L)
                .dtxsid("DTXSID7020182")
                .dtxcid("DTXCID30182")
                .smiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1")
                .canonQsarSmiles("CC(C)(C1C=CC(O)=CC=1)C1C=CC(O)=CC=1")
                .genericSubstanceUpdatedAt(LocalDateTime.of(2024, 8, 19, 8, 43, 20))
                .propName("pKa Acidic Apparent")
                .propCategory("Physchem")
                .propDescription("Strongest acidic acid dissociation constant")
                .modelName("OPERA_PKA_A")
                .modelId(1491L)
                .sourceName("OPERA2.8")
                .sourceDescription("<a href=\"https://github.com/kmansouri/OPERA/releases/tag/v2.8.4\">OPERA2.8</a> is a free and open source/open data suite of QSAR Models providing predictions and additional information including applicability domain and accuracy assessment, as described in the publication <a href=\"http://dx.doi.org/10.1186/s13321-018-0263-1\" target=\"_blank\">\"OPERA models for predicting physicochemical properties and environmental fate endpoints\"</a>. All models were built on curated data and standardized chemical structures as described in the publication <a href=\"http://dx.doi.org/10.1080/1062936X.2016.1253611\" target=\"_blank\">\"An automated curation procedure for addressing chemical errors and inconsistencies in public datasets used in QSAR modelling\"</a>. All OPERA properties are predicted under ambient conditions of 760mm of Hg at 25 degrees Celsius.\n")
                .propValueExperimental(null)
                .propValueExperimentalString(null)
                .propValue(9.46)
                .propUnit("Log10 unitless")
                .propValueString(null)
                .propValueError(null)
                .adMethod("Combined Applicability Domain")
                .adValue(null)
                .adConclusion(null)
                .adReasoning("Inside training set (Global AD = 1) and good local representation (Local AD index = 0.687 &gt; 0.6)")
                .adMethodGlobal("OPERA Global Index")
                .adValueGlobal((double) 1)
                .adConclusionGlobal("Inside")
                .adReasoningGlobal("Inside AD since value = 1")
                .hasQmrf(false)
                .qmrfUrl(null)
                .build();

        propertyExperimental = ChemicalPropertyExperimental.builder()
                .id(303415L)
                .dtxsid("DTXSID7020182")
                .dtxcid("DTXCID30182")
                .smiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1")
                .propName("Density")
                .dataset("exp_prop_DENSITY_v2.0")
                .propValue(1.2)
                .propUnit("g/cm^3")
                .propValueId(417213L)
                .propValueOriginal("{\"Value\":\"1.2 g/cm^3\",\"Temperature\":\"25 �C\"}")
                .propValueText(null)
                .expDetailsTemperatureC((double) 25)
                .expDetailsPressureMmhg(null)
                .expDetailsPh(null)
                .expDetailsResponseSite(null)
                .expDetailsSpeciesLatin(null)
                .expDetailsSpeciesCommon(null)
                .expDetailsSpeciesSupercategory(null)
                .sourceName("eChemPortalAPI")
                .sourceDescription("eChemPortal is an online tool to retrieve REACH dossier data. Data was gathered from eChemPortal via API call. ")
                .publicSourceName("eChemPortalAPI")
                .publicSourceDescription("eChemPortal is an online tool to retrieve REACH dossier data. Data was gathered from eChemPortal via API call. ")
                .publicSourceUrl("https://www.echemportal.org/echemportal/property-search")
                .directUrl("https://echa.europa.eu/registration-dossier/-/registered-dossier/15752/4/5/?documentUUID=b01caee6-faf9-4c1e-a23d-d8d64779ef33")
                .lsName(null)
                .lsCitation(null)
                .lsDoi(null)
                .briefCitation(null)
                .publicSourceOriginalName(null)
                .publicSourceOriginalDescription(null)
                .publicSourceOriginalUrl(null)
                .build();

        propertyFate = new ChemicalFateAllDto(
                "Bioconcentration Factor",
                "{\"Value\":\"38.4 L/kg\",\"ResponseSite\":\"Liver\"}",
                "{\"Value\":\"43.651585 L/kg\"}"
        );

        propertyNames = projectionFactory.createProjection(ChemicalPropertyNames.class);
        propertyNames.setPropertyName("Androgen Receptor Agonist");

        propertySummary = projectionFactory.createProjection(ChemicalPropertySummary.class);
        propertySummary.setPropName("Boiling Point");
        propertySummary.setExperimentalAverage(313.5f);
        propertySummary.setExperimentalCount(3);
        propertySummary.setExperimentalMedian(360f);
        propertySummary.setExperimentalMin(220f);
        propertySummary.setExperimentalMax(360.5f);
        propertySummary.setPredictedAverage(371.9185f);
        propertySummary.setPredictedCount(2);
        propertySummary.setPredictedMedian(371.9185f);
        propertySummary.setPredictedMin(343f);
        propertySummary.setPredictedMax(400.837f);
        propertySummary.setUnit("°C");

        fateSummary = projectionFactory.createProjection(ChemicalPropertySummary.class);
        fateSummary.setPropName("Bioconcentration Factor");
        fateSummary.setExperimentalAverage(21.550144f);
        fateSummary.setExperimentalCount(11);
        fateSummary.setExperimentalMedian(13.3f);
        fateSummary.setExperimentalMin(1.7f);
        fateSummary.setExperimentalMax(67.7f);
        fateSummary.setPredictedAverage(43.651585f);
        fateSummary.setPredictedCount(1);
        fateSummary.setPredictedMedian(43.651585f);
        fateSummary.setPredictedMin(43.651585f);
        fateSummary.setPredictedMax(43.651585f);
        fateSummary.setUnit("L/kg");
    }

    @Test
    void testGetExpermientalChemicalPropertiesByDtxsid() throws Exception {
        final List<ChemicalPropertyExperimental> properties = Collections.singletonList(propertyExperimental);

        when(experimentalRepository.findByDtxsid("DTXSID7020182", ChemicalPropertyExperimental.class)).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/experimental/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(propertyExperimental.getDtxsid()))
                .andExpect(jsonPath("$[0].propName").value(propertyExperimental.getPropName()));
    }

    @Test
    void testGetExpermientalPropertiesByBatchDtxsid() throws Exception {
        final List<ChemicalPropertyExperimental> properties = Collections.singletonList(propertyExperimental);
        String[] jsonArray = {"DTXSID7020182"};
        String jsonBody = new JsonMapper().writeValueAsString(jsonArray);

        when(experimentalRepository.findByDtxsidInOrderByDtxsidAsc(jsonArray, ChemicalPropertyExperimental.class)).thenReturn(properties);

        mockMvc.perform(post("/chemical/property/experimental/search/by-dtxsid/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(propertyExperimental.getDtxsid()))
                .andExpect(jsonPath("$[0].propName").value(propertyExperimental.getPropName()))
                .andReturn();
        ;
    }


    @Test
    void testGetExperimentalPropertiesByRange() throws Exception {
        final List<ChemicalPropertyExperimental> properties = Collections.singletonList(propertyExperimental);

        when(experimentalRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc("Density", 1.0, 1.5, ChemicalPropertyExperimental.class)).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/experimental/search/by-range/Density/1/1.5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(propertyExperimental.getDtxsid()))
                .andExpect(jsonPath("$[0].propName").value(propertyExperimental.getPropName()));
    }

    @Test
    void testGetExperimentalPropertyNames() throws Exception {
        final List<ChemicalPropertyNames> properties = Collections.singletonList(propertyNames);

        when(experimentalRepository.getExperimentalPropertiesList()).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/experimental/name"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propertyName").value(propertyNames.getPropertyName()));
    }

    @Test
    void testGetPredictedPropertiesByDtxsid() throws Exception {
        final List<ChemicalPropertyPredicted> properties = Collections.singletonList(predictedProperty);

        when(predictedRepository.findByDtxsid("DTXSID7020182", ChemicalPropertyPredicted.class)).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/predicted/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(predictedProperty.getDtxsid()))
                .andExpect(jsonPath("$[0].propName").value(predictedProperty.getPropName()));
    }

    @Test
    void testGetPredictedPropertiesByBatchDtxsid() throws Exception {
        final List<ChemicalPropertyPredicted> properties = Collections.singletonList(predictedProperty);
        String[] jsonArray = {"DTXSID7020182"};
        String jsonBody = new JsonMapper().writeValueAsString(jsonArray);

        when(predictedRepository.findByDtxsidInOrderByDtxsidAsc(jsonArray, ChemicalPropertyPredicted.class)).thenReturn(properties);

        mockMvc.perform(post("/chemical/property/predicted/search/by-dtxsid/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(predictedProperty.getDtxsid()))
                .andExpect(jsonPath("$[0].propName").value(predictedProperty.getPropName()))
                .andReturn();
        ;
    }


    @Test
    void testGetPredictedPropertiesByRange() throws Exception {
        final List<ChemicalPropertyPredicted> properties = Collections.singletonList(predictedProperty);

        when(predictedRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc("pKa Acidic Apparent", 9.4, 9.5, ChemicalPropertyPredicted.class)).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/predicted/search/by-range/pKa Acidic Apparent/9.4/9.5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(predictedProperty.getDtxsid()))
                .andExpect(jsonPath("$[0].propName").value(predictedProperty.getPropName()));
    }

    @Test
    void testGetPredictedPropertyNames() throws Exception {
        final List<ChemicalPropertyNames> properties = Collections.singletonList(propertyNames);

        when(predictedRepository.getPredictedPropertiesList()).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/predicted/name"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propertyName").value(propertyNames.getPropertyName()));
    }

    @Test
    void testGetFateByDtxsid() throws Exception {
        final List<ChemicalFateAllDto> properties = Collections.singletonList(propertyFate);

        when(experimentalRepository.findFateByDtxsid("DTXSID7020182")).thenReturn(properties);

        mockMvc.perform(get("/chemical/fate/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propName").value(propertyFate.getPropName()));
    }


    @Test
    void testFateBatchSearchWithInvalidJsonLogging() throws Exception {
        // Test that malformed JSON triggers error logging
        String[] jsonArray = {"DTXSID7020182"};
        // This truly invalid JSON will cause JsonProcessingException
        String malformedJson = "[{invalid json here}]";
        Object[] mockRow = {"DTXSID7020182", malformedJson};

        when(experimentalRepository.findFateByDtxsidInOrderByDtxsidAsc(jsonArray))
                .thenReturn(Collections.singletonList(mockRow));

        mockMvc.perform(post("/chemical/fate/search/by-dtxsid/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(jsonArray)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value("DTXSID7020182"))
                .andExpect(jsonPath("$[0].properties").doesNotExist());
    }

    // These summaries contain values from both experimental and predicted Env. Fate/transport properties
    @Test
    void testGetFateSummaryByDtxsid() throws Exception {
        final List<ChemicalPropertySummary> properties = Collections.singletonList(fateSummary);

        when(predictedRepository.findSummaryByDtxsid("DTXSID7020182", "Env. Fate/transport")).thenReturn(properties);

        mockMvc.perform(get("/chemical/fate/summary/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propName").value(fateSummary.getPropName()));
    }

    @Test
    void testGetFateSummaryByDtxsidAndPropName() throws Exception {
        final List<ChemicalPropertySummary> properties = Collections.singletonList(fateSummary);

        when(predictedRepository.findSummaryByDtxsidAndPropName("DTXSID7020182", "Bioconcentration Factor", "Env. Fate/transport")).thenReturn(properties);

        mockMvc.perform(get("/chemical/fate/summary/search/")
                .param("dtxsid", "DTXSID7020182")
                .param("propName", "Bioconcentration Factor"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propName").value(fateSummary.getPropName()));
    }

    // These summaries contain values from both experimental and predicted Physchem properties
    @Test
    void testGetPropertySummaryByDtxsid() throws Exception {
        final List<ChemicalPropertySummary> properties = Collections.singletonList(propertySummary);

        when(predictedRepository.findSummaryByDtxsid("DTXSID7020182", "Physchem")).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/summary/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propName").value(propertySummary.getPropName()));
    }

    @Test
    void testGetPropertySummaryByDtxsidAndPropName() throws Exception {
        final List<ChemicalPropertySummary> properties = Collections.singletonList(propertySummary);

        when(predictedRepository.findSummaryByDtxsidAndPropName("DTXSID7020182", "Boiling Point", "Physchem")).thenReturn(properties);

        mockMvc.perform(get("/chemical/property/summary/search/")
                .param("dtxsid", "DTXSID7020182")
                .param("propName", "Boiling Point"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].propName").value(propertySummary.getPropName()));
    }

}
