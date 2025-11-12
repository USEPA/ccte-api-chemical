package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.dto.ChemicalFateAllDto;
import gov.epa.ccte.api.chemical.dto.ChemicalFateBatchDto;
import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.*;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyExperimentalRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyPredictedRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ChemicalPropertyResource implements ChemicalPropertyApi {

    private final ChemicalPropertyExperimentalRepository experimentalRepository;
    private final ChemicalPropertyPredictedRepository predictedRepository;

    
    @Value("${application.batch-size}")
    private Integer batchSize;

    public ChemicalPropertyResource(ChemicalPropertyExperimentalRepository experimentalRepository, ChemicalPropertyPredictedRepository predictedRepository) {
        this.experimentalRepository = experimentalRepository;
        this.predictedRepository = predictedRepository;

    }

 // *********************** Experimental - start *************************************
    @Override
    public List<ChemicalPropertyExperimental> experimentalPropertyByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);

        List<ChemicalPropertyExperimental> data =  experimentalRepository.findByDtxsid(dtxsid, ChemicalPropertyExperimental.class);
            
        return data;

    }

    @Override
    public List<ChemicalPropertyExperimental> experimentalPropertyByRange(String propertyName, Double start, Double end) {
        log.debug("property = {}, start = {}, end = {}", propertyName, start, end);
        
        List<ChemicalPropertyExperimental> data = experimentalRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc(propertyName, start, end, ChemicalPropertyExperimental.class);
    
        return data;
    }

    @Override
    public List<ChemicalPropertyNames> experimentalPropertyNames() {
        log.debug("experimental property names");
        
        return experimentalRepository.getExperimentalPropertiesList();
    }


    @Override
    public List<ChemicalPropertyExperimental> experimentalBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<ChemicalPropertyExperimental> data = experimentalRepository.findByDtxsidInOrderByDtxsidAsc(dtxsids, ChemicalPropertyExperimental.class);
        
        return data;
    }
    
    // *********************** Experimental - End *************************************
    // *********************** Predicted - start *************************************
    
    @Override
    public List<ChemicalPropertyPredicted> predictedPropertyByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);

        List<ChemicalPropertyPredicted> data =  predictedRepository.findByDtxsid(dtxsid, ChemicalPropertyPredicted.class);
         
        return data;

    }

    @Override
    public List<ChemicalPropertyPredicted> predictedPropertyByRange(String propertyName, Double start, Double end) {
        log.debug("property = {}, start = {}, end = {}", propertyName, start, end);
        
        List<ChemicalPropertyPredicted> data = predictedRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc(propertyName, start, end, ChemicalPropertyPredicted.class);
    
        return data;
    }

    @Override
    public List<ChemicalPropertyNames> predictedPropertyNames() {
        log.debug("experimental property names");
        
        return predictedRepository.getPredictedPropertiesList();
    }


    @Override
    public List<ChemicalPropertyPredicted> predictedBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<ChemicalPropertyPredicted> data = predictedRepository.findByDtxsidInOrderByDtxsidAsc(dtxsids, ChemicalPropertyPredicted.class);
        
        return data;
    }
    
    // *********************** Predicted - End *************************************
    // *********************** Property Summary - start *************************************
    
    @Override
    public List<ChemicalPropertySummary> propertySummaryByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);
        String propCategory = "Physchem";
        List<ChemicalPropertySummary> data =  predictedRepository.findSummaryByDtxsid(dtxsid, propCategory);
            
        return data;

    }
    
    @Override
    public List<ChemicalPropertySummary> propertySummaryByDtxsidAndName(String dtxsid, String propName) {
        log.info("dtxsid = {}, property name = {}", dtxsid, propName);
        String propCategory = "Physchem";
        List<ChemicalPropertySummary> data =  predictedRepository.findSummaryByDtxsidAndPropName(dtxsid, propName, propCategory);
            
        return data;

    }
    
    @Override
    public List<ChemicalPropertySummaryExperimental> propertySummaryExperimentalByDtxsidAndName(String dtxsid, String propName) {
		log.info("dtxsid = {}, property name = {}", dtxsid, propName);
		String propCategory = "Physchem";
		List<ChemicalPropertySummaryExperimental> data =  predictedRepository.findExpermentalSummaryByDtxsidAndPropName(dtxsid, propName, propCategory);
			
		return data;

	}
    
    @Override
    public List<ChemicalPropertySummaryPredicted> propertySummaryPredictedByDtxsidAndName(String dtxsid, String propName) {
		log.info("dtxsid = {}, property name = {}", dtxsid, propName);
		String propCategory = "Physchem";
		List<ChemicalPropertySummaryPredicted> data =  predictedRepository.findPredictedSummaryByDtxsidAndPropName(dtxsid, propName, propCategory);
			
		return data;

	}

    // *********************** Property Summary - End *************************************
    // *********************** Fate - Start *************************************
    
    @Override
    public List<ChemicalFateAllDto> fateByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);
        List<ChemicalFateAllDto> data = experimentalRepository.findFateByDtxsid(dtxsid);
        return data;
    }
    
    @Override
    public List<ChemicalFateBatchDto> fateBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<Object[]> results = experimentalRepository.findFateByDtxsidInOrderByDtxsidAsc(dtxsids);
        ObjectMapper mapper = new ObjectMapper();
        List<ChemicalFateBatchDto> data = new ArrayList<>();
        for (Object[] row : results) {
            String dtxsid = (String) row[0];
            String propertiesJson = (String) row[1];
            List<ChemicalFateBatchDto.PropertyDto> properties = null;
			try {
				properties = mapper.readValue(
				    propertiesJson,
				    new TypeReference<List<ChemicalFateBatchDto.PropertyDto>>() {}
				);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
            data.add(new ChemicalFateBatchDto(dtxsid, properties));
        }
        return data;
    }
    
    // *********************** Fate - end *************************************
    // *********************** Fate Summary - start *************************************
    
    @Override
    public List<ChemicalPropertySummary> fateSummaryByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);
        String propCategory = "Env. Fate/transport";
        List<ChemicalPropertySummary> data =  predictedRepository.findSummaryByDtxsid(dtxsid, propCategory);
            
        return data;

    }
    
    @Override
    public List<ChemicalPropertySummary> fateSummaryByDtxsidAndName(String dtxsid, String propName) {
        log.info("dtxsid = {}, property name = {}", dtxsid, propName);
        String propCategory = "Env. Fate/transport";
        List<ChemicalPropertySummary> data =  predictedRepository.findSummaryByDtxsidAndPropName(dtxsid, propName, propCategory);
            
        return data;

    }
    
    @Override
    public List<ChemicalPropertySummaryExperimental> fateSummaryExperimentalByDtxsidAndName(String dtxsid, String propName) {
		log.info("dtxsid = {}, property name = {}", dtxsid, propName);
		String propCategory = "Env. Fate/transport";
		List<ChemicalPropertySummaryExperimental> data =  predictedRepository.findExpermentalSummaryByDtxsidAndPropName(dtxsid, propName, propCategory);
			
		return data;

	}
    
    @Override
    public List<ChemicalPropertySummaryPredicted> fateSummaryPredictedByDtxsidAndName(String dtxsid, String propName) {
		log.info("dtxsid = {}, property name = {}", dtxsid, propName);
		String propCategory = "Env. Fate/transport";
		List<ChemicalPropertySummaryPredicted> data =  predictedRepository.findPredictedSummaryByDtxsidAndPropName(dtxsid, propName, propCategory);
			
		return data;

	}
    

}
