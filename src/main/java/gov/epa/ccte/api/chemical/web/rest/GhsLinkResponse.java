package gov.epa.ccte.api.chemical.web.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GhsLinkResponse {
    String dtxsid;
    Boolean isSafetyData;
    String safetyUrl;
}
