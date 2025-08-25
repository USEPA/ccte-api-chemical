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
@Table(name = "v_extra_data", schema = "ch")
public class ExtraData {
    @Id
    @Size(max = 45)
    @Column(name = "dtxsid", length = 45)
    private String dtxsid;

    @Size(max = 45)
    @Column(name = "cid", length = 45)
    private Integer dtxcid;

    @Size(max = 255)
    @Column(name = "refs")
    private Integer refs;

    @Column(name = "google_patent")
    private Integer googlePatent;

    @Column(name = "literature")
    private Integer literature;

    @Column(name = "pubmed")
    private Integer pubmed;
}