package gov.epa.ccte.api.chemical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import gov.epa.ccte.api.chemical.domain.ModelFiles;

@RepositoryRestResource(exported = false)
public interface ModelFilesRepository extends JpaRepository<ModelFiles, Long>{
    
    @Transactional(readOnly = true)
    @Query(nativeQuery = true,value = "select c.file_bytes from chemprop.mv_model_files c where c.model_id = :modelId and c.file_type_id = :typeId")
    byte[] getFileForModelIdAndTTypeId(@Param("modelId") Integer modelId, @Param("typeId") Integer typeId);
}
