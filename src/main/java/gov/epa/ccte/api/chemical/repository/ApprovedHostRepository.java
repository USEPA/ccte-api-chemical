package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ApprovedHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ApprovedHostRepository extends JpaRepository<ApprovedHost, Integer> {
}