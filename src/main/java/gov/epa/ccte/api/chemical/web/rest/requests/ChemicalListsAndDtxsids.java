package gov.epa.ccte.api.chemical.web.rest.requests;

import lombok.Data;

import java.util.List;

// This will be use for a hidden endpoint build for CCD batch search
@Data
public class ChemicalListsAndDtxsids {
    List<String> dtxsids;
    List<String> chemicalLists;
}
