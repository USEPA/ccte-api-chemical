package gov.epa.ccte.api.chemical.web.rest.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Page {
    private Integer size;
    private Long total;
    private Long next;
    private List<?> data;
}
