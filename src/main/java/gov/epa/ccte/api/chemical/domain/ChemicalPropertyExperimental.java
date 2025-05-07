package gov.epa.ccte.api.chemical.domain;

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
@Table(name = "mv_experimental_data", schema = "chemprop")
public class ChemicalPropertyExperimental {
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
    
    @Size(max = 1000)
    @Column(name = "smiles")
    private String smiles;
    
    @Size(max = 255)
    @Column(name = "prop_name")
    private String propName;
    
    @Size(max = 255)
    @Column(name = "dataset")
    private String dataset;
    
    @Size(max = 255)
    @Column(name = "prop_value")
    private Double propValue;
    
    @Column(name = "prop_unit")
    private String propUnit;
    
    @Column(name = "prop_value_id")
    private Long propValueId;
    
    @Size(max = 1000)
    @Column(name = "prop_value_original")
    private String propValueOriginal;
    
    @Size(max = 255)
    @Column(name = "prop_value_text")
    private String propValueText;
    
    @Column(name = "exp_details_temperature_c")
    private Double expDetailsTemperatureC;
    
    @Column(name = "exp_details_pressure_mmhg")
    private Double expDetailsPressureMmhg;
    
    @Column(name = "exp_details_ph")
    private Double expDetailsPh;
    
    @Size(max = 255)
    @Column(name = "exp_details_response_site")
    private String expDetailsResponseSite;
    
    @Size(max = 255)
    @Column(name = "exp_details_species_latin")
    private String expDetailsSpeciesLatin;
    
    @Size(max = 255)
    @Column(name = "exp_details_species_common")
    private String expDetailsSpeciesCommon;
    
    @Size(max = 255)
    @Column(name = "exp_details_species_supercategory")
    private String expDetailsSpeciesSupercategory;
    
    @Column(name = "source_name")
    private String sourceName;
    
    @Column(name = "source_description")
    private String sourceDescription;
    
    @Size(max = 255)
    @Column(name = "public_source_name")
    private String publicSourceName;
    
    @Size(max = 1000)
    @Column(name = "public_source_description")
    private String publicSourceDescription;
    
    @Size(max = 255)
    @Column(name = "public_source_url")
    private String publicSourceUrl;
    
    @Size(max = 1000)
    @Column(name = "direct_url")
    private String directUrl;
    
    @Size(max = 1000)
    @Column(name = "ls_name")
    private String lsName;
    
    @Size(max = 2000)
    @Column(name = "ls_citation")
    private String lsCitation;
    
    @Size(max = 255)
    @Column(name = "ls_doi")
    private String lsDoi;
    
    @Size(max = 100)
    @Column(name = "brief_citation")
    private String briefCitation;
    
    @Size(max = 255)
    @Column(name = "public_source_original_name")
    private String publicSourceOriginalName;
    
    @Size(max = 1000)
    @Column(name = "public_source_original_description")
    private String publicSourceOriginalDescription;
    
    @Size(max = 255)
    @Column(name = "public_source_original_url")
    private String publicSourceOriginalUrl;
    
}
