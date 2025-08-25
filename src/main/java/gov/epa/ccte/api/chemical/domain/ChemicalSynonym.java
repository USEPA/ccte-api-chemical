package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "v_chemical_snonyms", schema = "ch")
public class ChemicalSynonym {
    @Id
    @Size(max = 45)
    @Column(name = "dtxsid", length = 45)
    private String dtxsid;

    @Column(name = "pc_code")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String pcCode;

    @Column(name = "valid_synonym")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String validSynonym;

    @Column(name = "good_synonym")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String goodSynonyms;

    @Column(name = "deleted_synonym")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String deleteSynonyms;

    @Column(name = "other_synonym")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String otherSynonyms;

    @Column(name = "beilstein_synonym")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String beilsteinSynonyms;

    @Column(name = "alternate_synonym")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String alternateSynonyms;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "import_date")
    private OffsetDateTime importDate;

}