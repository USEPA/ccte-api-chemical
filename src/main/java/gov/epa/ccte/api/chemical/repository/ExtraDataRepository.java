package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ExtraData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ExtraDataRepository extends JpaRepository<ExtraData, String> {
    @Transactional(readOnly = true)
    <T> List<T> findByDtxsid(String dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    <T> List<T> findByDtxsidInOrderByDtxsidAsc(String[] dtxsids, Class<T> type);

}
