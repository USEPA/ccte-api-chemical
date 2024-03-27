package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "v_chemical_lists", schema = "ch")
public class ChemicalList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "list_name", length = 50)
    private String listName;

    @Size(max = 255)
    @Column(name = "label")
    private String label;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @Size(max = 255)
    @Column(name = "visibility")
    private String visibility;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Size(max = 500)
    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "long_description", length = Integer.MAX_VALUE)
    private String longDescription;

    @Column(name = "chemical_count")
    private Long chemicalCount;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;

}