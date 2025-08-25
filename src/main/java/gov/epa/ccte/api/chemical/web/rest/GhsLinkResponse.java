package gov.epa.ccte.api.chemical.web.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GhsLinkResponse {
    String dtxsid;
    Boolean isSafetyData;
    String safetyUrl;
}
