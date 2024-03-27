package gov.epa.ccte.api.chemical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
@Data
@Schema
public class ChemicalDetailDto implements Serializable {
    @Size(max = 64)
    private final String id;
    private final String dtxsid;
    private final String dtxcid;
    @Size(max = 255)
    private final String casrn;
    private final Integer compoundId;
    private final Integer genericSubstanceId;
    @Size(max = 255)
    private final String preferredName;
    private final Integer activeAssays;
    private final Long cpdataCount;
    @Size(max = 255)
    private final String molFormula;
    private final Double monoisotopicMass;
    private final Double percentAssays;
    private final Integer pubchemCount;
    private final Double pubmedCount;
    private final Long sourcesCount;
    private final Integer qcLevel;
    @Size(max = 255)
    private final String qcLevelDesc;
    @Size(max = 1)
    private final String stereo;
    private final Integer isotope;
    private final Integer multicomponent;
    private final Integer totalAssays;
    private final String toxcastSelect;
    private final Integer pubchemCid;
//    private final String molFile;
//    private final String mrvFile;
    private final Long relatedSubstanceCount;
    private final Long relatedStructureCount;
//    private final byte[] molImage;
    private final Integer hasStructureImage;
    @Size(max = 5000)
    private final String iupacName;
    private final String smiles;
    private final String inchiString;
    private final Double averageMass;
    @Size(max = 255)
    private final String inchikey;
    @Size(max = 4000)
    private final String qcNotes;
    private final String qsarReadySmiles;
    private final String msReadySmiles;
    @Size(max = 255)
    private final String irisLink;
    @Size(max = 255)
    private final String pprtvLink;
    @Size(max = 255)
    private final String wikipediaArticle;
    @Size(max = 20000)
    private final String descriptorStringTsv;
    private final Boolean isMarkush;
    @NotNull
    private final Instant dateLoaded;
}