package gov.epa.ccte.api.chemical.web.rest.requests;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.ChemicalDetailStandard2;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Page {
    private Integer size=2000;
    private Long total;
    private Long next;
    private List<?> data;
}
