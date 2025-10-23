package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "approved_hosts", schema = "app")
public class ApprovedHost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approved_hosts_id_gen")
    @SequenceGenerator(name = "approved_hosts_id_gen", sequenceName = "approved_hosts_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "host_name", length = Integer.MAX_VALUE)
    private String hostName;

}