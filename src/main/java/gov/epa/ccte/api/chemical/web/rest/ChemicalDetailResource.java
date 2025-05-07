package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.*;
import gov.epa.ccte.api.chemical.service.ChemicalDetailService;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import gov.epa.ccte.api.chemical.web.rest.requests.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class ChemicalDetailResource implements ChemicalDetailApi {
    
	private final ChemicalDetailService detailService;
    @Value("${application.batch-size}")
    private Integer batchSize;
	
    private Long totalChemicals;
	
    public ChemicalDetailResource(ChemicalDetailService detailService) {
        this.detailService = detailService;
        totalChemicals = detailService.getTotalChemicals();
    }

    
    @Override
    public Page getAllChemicalDetails(Long next, String projection) {
        log.debug("nextCursor: {}, totalChemicals = {}", next, totalChemicals);
        
        return detailService.getAllChemicals(next, batchSize, totalChemicals, projection);
    }
    
    @Override
    public ChemicalDetailBase detailByDtxsid( String dtxsid, ChemicalDetailProjection projection) {
        log.debug("dtxsid = {}", dtxsid);

        List data = getChemicalDetails(new String[]{dtxsid}, "dtxsid", projection);

        if(data.isEmpty())
            throw  new IdentifierNotFoundException("dtxsid", dtxsid);
        else
            return (ChemicalDetailBase) data.get(0);
    }

    
    @Override
    public ChemicalDetailBase detailsByDtxcid(String dtxcid, ChemicalDetailProjection projection) {
        log.debug("dtxcid = {}", dtxcid);

        List data = getChemicalDetails(new String[]{dtxcid}, "dtxcid", projection);

        if(data.isEmpty())
            throw  new IdentifierNotFoundException("dtxcid", dtxcid);
        else
            return (ChemicalDetailBase) data.get(0);
    }
    
    @Override
    public List batchDtxsidSearch(String[] dtxsids, ChemicalDetailProjection projection) {

        log.debug("dtxsids = {}", dtxsids.length);

        if(dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid" );

        List data =  getChemicalDetails(dtxsids, "dtxsid", projection);
        log.debug("database return {}", data.size());

        return data;
    }
   
    @Override
    public List batchDtxcidSearch(String[] dtxcids, ChemicalDetailProjection projection) {

        log.debug("dtxcids = {}", dtxcids.length);

        if(dtxcids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxcids.length, batchSize, "dtxcid" );

        List data =  getChemicalDetails(dtxcids, "dtxcid", projection);
        log.debug("database return {}", data.size());

        return data;
    }

    private List getChemicalDetails(String[] ids, String type, ChemicalDetailProjection projection) {
        return switch (projection) {
            case chemicaldetailall ->
                    detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, type);
            case chemicaldetailstandard ->
                    detailService.getChemicalDetailsForBatch(ids, ChemicalDetailStandard.class, type);
            case chemicalidentifier ->
                    detailService.getChemicalDetailsForBatch(ids, ChemicalIdentifier.class, type);
            case chemicalstructure ->
                    detailService.getChemicalDetailsForBatch(ids, ChemicalStructure.class, type);
            case ntatoolkit -> detailService.getChemicalDetailsForBatch(ids, NtaToolkit.class, type);
            case ccdchemicaldetails -> detailService.getChemicalDetailsForBatch(ids, CcdChemicalDetails.class, type);
            case compact -> detailService.getChemicalDetailsForBatch(ids, Compact.class, type);
        };
    }
}


