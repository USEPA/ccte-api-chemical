package gov.epa.ccte.api.chemical.service;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailAllIds;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailStandard2;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.web.rest.requests.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChemicalDetailService {
    private final ChemicalDetailRepository detailRepository;

    public ChemicalDetailService(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }


    public  <T> List<T> getChemicalDetailsForBatch(String[] ids, Class<T> tClass, String type) {

        if(type.equalsIgnoreCase("dtxsid"))
            return detailRepository.findByDtxsidInOrderByDtxsidAsc(ids, tClass);
        else
            return detailRepository.findByDtxcidInOrderByDtxcidAsc(ids, tClass);
    }

    public Page getAllChemicals(Long nextCursor, Integer batchSize, Long totalChemicals, String projection) {

        log.debug("next cursor: " + nextCursor);
        if (projection == null || projection.isEmpty()) {
        	List<ChemicalDetailStandard2> data = detailRepository.findByIdGreaterThanAndDtxsidNotNull(nextCursor, Limit.of(batchSize), ChemicalDetailStandard2.class);
        	log.debug("data size: {}", data.size());        
        	Page results = Page.builder()
                .data(data)
                .size(batchSize)
                .total(totalChemicals)
                .next(maximumId(data))
                .build();
        	return results;
        }else {
        	List<ChemicalDetailAllIds> data = detailRepository.findByIdGreaterThanAndDtxsidNotNull(nextCursor, Limit.of(batchSize), ChemicalDetailAllIds.class);
        	log.debug("data size: {}", data.size());
        	Page results = Page.builder()
                .data(data)
                .size(batchSize)
                .total(totalChemicals)
                .next(maximumId2(data))
                .build();
        	return results; 
        	}

    	}

    private Long maximumId(List<ChemicalDetailStandard2> data) {
        if(!data.isEmpty()){
            return (data.get(data.size()-1)).getId();
        }else{
            return 1L;
        }
    }

    private Long maximumId2(List<ChemicalDetailAllIds> data) {
        if(!data.isEmpty()){
            return (data.get(data.size()-1)).getId();
        }else{
            return 1L;
        }
    }

    public Long getTotalChemicals() {
        return detailRepository.count();
    }
}
