package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_chemical_lists_new", schema = "ch")
public class ChemicalList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "list_name", length = 50)
    private String listName;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;
    
    @Size(max = 500)
    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "long_description", length = Integer.MAX_VALUE)
    private String longDescription;

    @Column(name = "chemical_count")
    private Long chemicalCount;

    @Column(name = "updated_at")
    private Instant updatedAt;


}