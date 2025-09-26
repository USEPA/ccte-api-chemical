package gov.epa.ccte.api.chemical.projection.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CcdChemicalSearchResult {

    @JsonProperty("dtxsid")
    private String dtxsid;

    // column for UI - it is not in table
    @Transient
    @JsonProperty(value = "selected", required = false, defaultValue = "false")
    private Boolean selected = false;

    @JsonProperty("dtxcid")
    private String dtxcid;

    @JsonProperty("genericSubstanceId")
    private Integer genericSubstanceId;

    @JsonProperty("casrn")
    private String casrn;

    @JsonProperty("preferredName")
    private String preferredName;

    @JsonProperty("compoundId")
    private Integer compoundId;

    @JsonProperty("stereo")
    private Integer stereo;

    @JsonProperty("isotope")
    private Integer isotope;

    @JsonProperty("multicomponent")
    private Integer multicomponent;

    @JsonProperty("pubchemCount")
    private Integer pubchemCount;

    @JsonProperty("pubmedCount")
    private Integer pubmedCount;

    @JsonProperty("sourcesCount")
    private Integer sourcesCount;

    @JsonProperty("cpdataCount")
    private Long cpdataCount;

    @JsonProperty("activeAssays")
    private Integer activeAssays;

    @JsonProperty("totalAssays")
    private Integer totalAssays;

    @JsonProperty("percentAssays")
    private BigInteger percentAssays;

    @JsonProperty("toxcastSelect")
    private String toxcastSelect;

    @JsonProperty("monoisotopicMass")
    private Double monoisotopicMass;

    @JsonProperty("molFormula")
    private String molFormula;

    @JsonProperty("qcLevel")
    private Integer qcLevel;

    @JsonProperty("qcLevelDesc")
    private String qcLevelDesc;

    @JsonProperty("pubchemCid")
    private Integer pubchemCid;

    @JsonProperty("hasStructureImage")
    private Boolean hasStructureImage;

    @JsonProperty("relatedSubstanceCount")
    private Integer relatedSubstanceCount;

    @JsonProperty("relatedStructureCount")
    private Integer relatedStructureCount;

    @JsonProperty("iupacName")
    private String iupacName;

    @JsonProperty("smiles")
    private String smiles;

    @JsonProperty("inchiString")
    private String inchiString;

    @JsonProperty("inchikey")
    private String inchikey;

    @JsonProperty("averageMass")
    private Double averageMass;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("searchMatch")
    private String searchMatch;

    @JsonProperty("searchWord")
    private String searchWord;

    public CcdChemicalSearchResult(String dtxsid, String dtxcid, Integer genericSubstanceId, String casrn, String preferredName, Integer compoundId, Integer stereo, Integer isotope, Integer multicomponent, Integer pubchemCount, Integer pubmedCount, Integer sourcesCount, Long cpdataCount, Integer activeAssays, Integer totalAssays, BigInteger percentAssays, String toxcastSelect, Double monoisotopicMass, String molFormula, Integer qcLevel, String qcLevelDesc, Integer pubchemCid, Boolean hasStructureImage, Integer relatedSubstanceCount, Integer relatedStructureCount, String iupacName, String smiles, String inchiString, String inchikey, Double averageMass, Integer rank, String searchMatch, String searchWord) {
        this.dtxsid = dtxsid;
        this.selected = false;
        this.dtxcid = dtxcid;
        this.genericSubstanceId = genericSubstanceId;
        this.casrn = casrn;
        this.preferredName = preferredName;
        this.compoundId = compoundId;
        this.stereo = stereo;
        this.isotope = isotope;
        this.multicomponent = multicomponent;
        this.pubchemCount = pubchemCount;
        this.pubmedCount = pubmedCount;
        this.sourcesCount = sourcesCount;
        this.cpdataCount = cpdataCount;
        this.activeAssays = activeAssays;
        this.totalAssays = totalAssays;
        this.percentAssays = percentAssays;
        this.toxcastSelect = toxcastSelect;
        this.monoisotopicMass = monoisotopicMass;
        this.molFormula = molFormula;
        this.qcLevel = qcLevel;
        this.qcLevelDesc = qcLevelDesc;
        this.pubchemCid = pubchemCid;
        this.hasStructureImage = hasStructureImage;
        this.relatedSubstanceCount = relatedSubstanceCount;
        this.relatedStructureCount = relatedStructureCount;
        this.iupacName = iupacName;
        this.smiles = smiles;
        this.inchiString = inchiString;
        this.inchikey = inchikey;
        this.averageMass = averageMass;
        this.rank = rank;
        this.searchMatch = searchMatch;
        this.searchWord = searchWord;
    }    
}