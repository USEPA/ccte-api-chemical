package gov.epa.ccte.api.chemical.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mv_model_files", schema = "chemprop")
public class ModelFiles {
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "model_id")
    private Integer modelId;

    @Size(max = 255)
    @Column(name = "model_name")
    private String modelName;
    
    @Size(max = 255)
    @Column(name = "model_source")
    private String modelSource;

    @Column(name = "file_type_id")
    private Integer typeId;
    
    @Size(max = 255)
    @Column(name = "file_type_name")
    private String typeName;
    
    @Size(max = 255)
    @Column(name = "property_name")
    private String propertyName;
    
    @Column(name = "file_bytes")
    private byte[] fileBytes;
}