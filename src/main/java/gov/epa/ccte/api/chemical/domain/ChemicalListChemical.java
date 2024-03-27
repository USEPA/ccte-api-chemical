package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "v_chemical_list_chemicals", schema = "ch")
public class ChemicalListChemical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "list_id")
    private Integer listId;

    @Size(max = 50)
    @Column(name = "list_name", length = 50)
    private String listName;

    @Size(max = 255)
    @Column(name = "dtxsid")
    private String dtxsid;

    @Column(name = "is_public")
    private Boolean isPublic;

}