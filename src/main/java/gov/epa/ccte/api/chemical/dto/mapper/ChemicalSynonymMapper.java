package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ChemicalSynonym;
import gov.epa.ccte.api.chemical.dto.ChemicalSynonymDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChemicalSynonymMapper {
    ChemicalSynonym toEntity(ChemicalSynonymDto chemicalSynonymDto);

    ChemicalSynonymDto toDto(ChemicalSynonym chemicalSynonym);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChemicalSynonym partialUpdate(ChemicalSynonymDto chemicalSynonymDto, @MappingTarget ChemicalSynonym chemicalSynonym);
}