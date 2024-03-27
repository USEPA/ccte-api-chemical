package gov.epa.ccte.api.chemical.service;

import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListWithDtxsids;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChemicalDetailService {
    private final ChemicalDetailRepository detailRepository;
    private final ChemicalListRepository listRepository;

    public ChemicalDetailService(ChemicalDetailRepository detailRepository, ChemicalListRepository listRepository) {
        this.detailRepository = detailRepository;
        this.listRepository = listRepository;    }

    public <T> T getChemicalDetailsForId(String id, String type, Class<T> tClass) {

        if(type.equals("dtxsid")) {
            return detailRepository.findByDtxsid(id, tClass)
                    .orElseThrow(() -> new IdentifierNotFoundException("dtxsid", id));
        }else{
            return detailRepository.findByDtxcid(id, tClass)
                    .orElseThrow(() -> new IdentifierNotFoundException("dtxcid", id));
        }
    }

    public  <T> List<T> getChemicalDetailsForBatch(String[] dtxsids, Class<T> tClass, String type) {

        if(type.equalsIgnoreCase("dtxsid"))
            return detailRepository.findByDtxsidInOrderByDtxsidAsc(dtxsids, tClass);
        else
            return detailRepository.findByDtxcidInOrderByDtxcidAsc(dtxsids, tClass);
    }

    public <T> List<T> getChemicalDetailsForListName(String listName, Class<T> tClass) {

        // first get list of dtxsids for chemical list members
        ChemicalListWithDtxsids list = listRepository.getListWithDtxsidsByListName(listName, "PUBLIC").orElseThrow(() -> new IdentifierNotFoundException("List name", listName));

        return detailRepository.findByDtxsidInOrderByDtxsidAsc(list.getDtxsids().split(","), tClass);
    }
}
