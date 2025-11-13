package gov.epa.ccte.api.chemical.web.rest;

import static gov.epa.ccte.api.chemical.domain.MimeType.EXCEL_XLSX;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.IMAGE_PNG;

import java.util.*;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import gov.epa.ccte.api.chemical.repository.ModelFilesRepository;
import gov.epa.ccte.api.chemical.repository.ModelReportsRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ChemicalPropertyModelResource implements ChemicalPropertyModelApi {

    private final ModelFilesRepository filesRepository;
    private final ModelReportsRepository reportsRepository;



    public ChemicalPropertyModelResource(ModelFilesRepository filesRepository, ModelReportsRepository reportsRepository) {
    	this.filesRepository = filesRepository;
    	this.reportsRepository = reportsRepository;
}

    @Override
    public List getModelReportByDtxsid(String dtxsid) {
    	log.debug("model report for dtxsid = {}", dtxsid);

    	List data = reportsRepository.findByDtxsid(dtxsid);

    	return data;
    }
    
    @Override
    public ResponseEntity<byte[]> getModelFileByModelIdAndTypeId(Integer modelId, Integer typeId) {
    	log.debug("model file image for modelId = {} and typeId = {}", modelId, typeId);

    	byte[] image = filesRepository.getFileForModelIdAndTTypeId(modelId, typeId);

        return switch (typeId) {
            case 1 -> ResponseEntity.ok().contentType(APPLICATION_PDF).body(image);
            case 2 -> ResponseEntity.ok().contentType(EXCEL_XLSX).body(image);
            case 3 -> ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
            case 4 -> ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
            default -> null;
        };
    }
    
    @Override
    public String getModelHTMLByDtxsid(String dtxsid, Long modelId) {
    	log.debug("model report for dtxsid = {} modelid = {}", dtxsid, modelId);

    	String data = reportsRepository.findReportHtmlByDtxsidAndModelId(dtxsid, modelId);

    	return data;
    }
    
    @Override
    public String getModelJSONByDtxsid(String dtxsid, Long modelId) {
    	log.debug("model report for dtxsid = {} modelid = {}", dtxsid, modelId);

    	String data = reportsRepository.findReportJsonByDtxsidAndModelId(dtxsid, modelId);

    	return data;
    }
}
