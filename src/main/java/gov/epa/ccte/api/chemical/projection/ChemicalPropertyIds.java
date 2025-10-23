package gov.epa.ccte.api.chemical.projection;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalProperty} entity
 */
@Schema(name = "ChemicalPropertyIds", description = "Attributes for chemical property id for chemical property APIs")
public interface ChemicalPropertyIds {
    String getPropType();

    String getName();

    String getPropertyId();
}