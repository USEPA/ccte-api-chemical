package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalSynonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "chemicalSynonym", path = "chemical-synonym", itemResourceRel = "chemicalSynonym", exported = false)
public interface ChemicalSynonymRepository extends JpaRepository<ChemicalSynonym, String> {
    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    <T>
    Optional<T> findByDtxsid(String dtxsid, Class<T> type);

}