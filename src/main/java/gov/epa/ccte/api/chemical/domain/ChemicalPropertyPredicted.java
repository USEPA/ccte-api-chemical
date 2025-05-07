package gov.epa.ccte.api.chemical.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mv_predicted_data", schema = "chemprop")
public class ChemicalPropertyPredicted {
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "dtxsid")
    private String dtxsid;
    
    @Size(max = 255)
    @Column(name = "dtxcid")
    private String dtxcid;
    
    @Size(max = 2000)
    @Column(name = "smiles")
    private String smiles;
    
    @Size(max = 1000)
    @Column(name = "canon_qsar_smiles")
    private String canonQsarSmiles;
    
    @Column(name = "generic_substance_updated_at")
    private Date genericSubstanceUpdatedAt;
    
    @Size(max = 255)
    @Column(name = "prop_name")
    private String propName;
    
    @Size(max = 255)
    @Column(name = "prop_category")
    private String propCategory;
    
    @Size(max = 255)
    @Column(name = "property_description")
    private String propDescription;
    
    @Size(max = 255)
    @Column(name = "model_name")
    private String modelName;
    
    @Column(name = "model_id")
    private Long modelId;
    
    @Size(max = 255)
    @Column(name = "source_name")
    private String sourceName;
    
    @Size(max = 1000)
    @Column(name = "source_description")
    private String sourceDescription;
    
    @Column(name = "prop_value_experimental")
    private Double propValueExperimental;
    
    @Size(max = 255)
    @Column(name = "prop_value_experimental_string")
    private String propValueExperimentalString;
    
    @Column(name = "prop_value")
    private Double propValue;
    
    @Column(name = "prop_unit")
    private String propUnit;
    
    @Size(max = 255)
    @Column(name = "prop_value_string")
    private String propValueString;
    
    @Size(max = 255)
    @Column(name = "prop_value_error")
    private String propValueError;
    
    @Size(max = 255)
    @Column(name = "ad_method")
    private String adMethod;
    
    @Column(name = "ad_value")
    private Double adValue;
    
    @Size(max = 255)
    @Column(name = "ad_conclusion")
    private String adConclusion;
    
    @Size(max = 255)
    @Column(name = "ad_reasoning")
    private String adReasoning;
    
    @Column(name = "ad_method_global")
    private String adMethodGlobal;
    
    @Column(name = "ad_value_global")
    private Double adValueGlobal;
    
    @Size(max = 255)
    @Column(name = "ad_conclusion_global")
    private String adConclusionGlobal;
    
    @Size(max = 255)
    @Column(name = "ad_reasoning_global")
    private String adReasoningGlobal;
    
    @Column(name = "has_qmrf")
    private Boolean hasQmrf;
    
    @Column(name = "qmrf_url")
    private String qmrfUrl;
    
}
