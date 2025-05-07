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

@Table(name = "mv_predicted_reports", schema = "chemprop")
public class ModelReports {
    @Id
    @Column(name = "id")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "dtxsid")
    private String dtxsid;

    @Size(max = 255)
    @Column(name = "dtxcid")
    private String dtxcid;

    @Column(name = "model_id")
    private Long modelId;

    @Size(max = 255)
    @Column(name = "model_name")
    private String modelName;

    @Size(max = 255)
    @Column(name = "prop_name")
    private String propertyName;

    @Size(max = 255)
    @Column(name = "prop_category")
    private String propertyCategory;

    @Size(max = 255)
    @Column(name = "source_name")
    private String sourceName;
    
    @Column(name = "report_json")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String reportJson;

    @Column(name = "report_html")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String reportHtml;
}
