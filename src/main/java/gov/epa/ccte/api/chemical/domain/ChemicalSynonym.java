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

@Getter
@Setter
@Entity
@Table(name = "chemical_synonym", schema = "ms")
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

    @Column(name = "good_synonyms")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String goodSynonyms;

    @Column(name = "delete_synonyms")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String deleteSynonyms;

    @Column(name = "other_synonyms")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String otherSynonyms;

    @Column(name = "beilstein_synonyms")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String beilsteinSynonyms;

    @Column(name = "alternate_synonyms")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String alternateSynonyms;

}