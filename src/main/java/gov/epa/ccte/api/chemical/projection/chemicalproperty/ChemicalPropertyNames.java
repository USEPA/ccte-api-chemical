package gov.epa.ccte.api.chemical.projection.chemicalproperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalProperty} entity
 */
@Schema(name = "ChemicalPropertyName", description = "Attributes for chemical property names for chemical property APIs")
public interface ChemicalPropertyNames {
	
    String getPropertyName();
    
}