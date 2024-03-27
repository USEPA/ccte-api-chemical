package gov.epa.ccte.api.chemical.projection.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
//@RequiredArgsConstructor
public class CcdChemicalSearchResult {

    @JsonProperty("dtxsid")
    final private String dtxsid;

    // column for UI - it is not in table
    @Transient
    @JsonProperty(value = "selected", required = false, defaultValue = "false")
    private Boolean selected = false;

    @JsonProperty("dtxcid")
    final private String dtxcid;

    @JsonProperty("genericSubstanceId")
    final private Integer genericSubstanceId;

    @JsonProperty("casrn")
    final private String casrn;

    @JsonProperty("preferredName")
    final private String preferredName;

    @JsonProperty("compoundId")
    final private Integer compoundId;

    @JsonProperty("stereo")
    final private Integer stereo;

    @JsonProperty("isotope")
    final private Integer isotope;

    @JsonProperty("multicomponent")
    final private Integer multicomponent;

    @JsonProperty("pubchemCount")
    final private Integer pubchemCount;

    @JsonProperty("pubmedCount")
    final private Integer pubmedCount;

    @JsonProperty("sourcesCount")
    final private Integer sourcesCount;

    @JsonProperty("cpdataCount")
    final private Long cpdataCount;

    @JsonProperty("activeAssays")
    final private Integer activeAssays;

    @JsonProperty("totalAssays")
    final private Integer totalAssays;

    @JsonProperty("percentAssays")
    final private BigInteger percentAssays;

    @JsonProperty("toxcastSelect")
    final private String toxcastSelect;

    @JsonProperty("monoisotopicMass")
    final private Double monoisotopicMass;

    @JsonProperty("molFormula")
    final private String molFormula;

    @JsonProperty("qcLevel")
    final private Integer qcLevel;

    @JsonProperty("qcLevelDesc")
    final private String qcLevelDesc;

    @JsonProperty("pubchemCid")
    final private Integer pubchemCid;

    @JsonProperty("hasStructureImage")
    final private Boolean hasStructureImage;

    @JsonProperty("relatedSubstanceCount")
    final private Integer relatedSubstanceCount;

    @JsonProperty("relatedStructureCount")
    final private Integer relatedStructureCount;

    @JsonProperty("iupacName")
    final private String iupacName;

    @JsonProperty("smiles")
    final private String smiles;

    @JsonProperty("inchiString")
    final private String inchiString;

    @JsonProperty("inchiKey")
    final private String inchikey;

    @JsonProperty("averageMass")
    final private Double averageMass;

    @JsonProperty("rank")
    final private Integer rank;

    @JsonProperty("searchMatch")
    final private String searchMatch;

    @JsonProperty("searchWord")
    final private String searchWord;

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
