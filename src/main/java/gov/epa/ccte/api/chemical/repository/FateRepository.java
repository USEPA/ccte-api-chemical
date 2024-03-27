package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.Fate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "fates", path = "fates", itemResourceRel = "fate", exported = false)
public interface FateRepository extends JpaRepository<Fate, Integer> {
    <T> List<T> findByDtxsidInOrderByDtxsidAscEndpointNameAsc(String[] dtxsids, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    <T>
    List<T> findByDtxsid(String dtxsid, Class<T> type);
}

