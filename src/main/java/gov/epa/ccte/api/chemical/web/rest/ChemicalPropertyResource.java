package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyAll;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertySummary;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyExperimentalRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyPredictedRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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
    public List<ChemicalPropertyAll> experimentalPropertyByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);

        List<ChemicalPropertyAll> data =  experimentalRepository.findExperimentalByDtxsid(dtxsid);
            
        return data;

    }

    @Override
    public List<ChemicalPropertyAll> experimentalPropertyByRange(String propertyName, Double start, Double end) {
        log.debug("property = {}, start = {}, end = {}", propertyName, start, end);
        
        List<ChemicalPropertyAll> data = experimentalRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc(propertyName, start, end, ChemicalPropertyAll.class);
    
        return data;
    }

    @Override
    public List<ChemicalPropertyNames> experimentalPropertyNames() {
        log.debug("experimental property names");
        
        return experimentalRepository.getExperimentalPropertiesList();
    }


    @Override
    public List<ChemicalPropertyAll> experimentalBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<ChemicalPropertyAll> data = experimentalRepository.findExperimentalByDtxsidInOrderByDtxsidAsc(dtxsids);
        
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

    // *********************** Property Summary - End *************************************
    // *********************** Fate - Start *************************************
    
    @Override
    public List<ChemicalPropertyAll> fateByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);

        List<ChemicalPropertyAll> data =  experimentalRepository.findFateByDtxsid(dtxsid);
            
        return data;

    }
    
    @Override
    public List<ChemicalPropertyAll> fateBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<ChemicalPropertyAll> data = experimentalRepository.findFateByDtxsidInOrderByDtxsidAsc(dtxsids);
        
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
    
}