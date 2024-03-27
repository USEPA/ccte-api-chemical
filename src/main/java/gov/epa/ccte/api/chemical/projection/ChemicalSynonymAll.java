package gov.epa.ccte.api.chemical.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Value;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalSynonym} entity
 */
@Schema(name = "ChemicalSynonymAll", description = "Different types of synonyms for request dtxsid")
public interface ChemicalSynonymAll {

    String getDtxsid();

    String getPcCode();

    @Value("#{target.ValidSynonym == null? null : target.ValidSynonym.split(\"\\|\")}")
    String[] getValid();

    @Value("#{target.GoodSynonyms == null? null : target.GoodSynonyms.split(\"\\|\")}")
    String[] getGood();

    @Value("#{target.DeleteSynonyms == null? null : target.DeleteSynonyms.split(\"\\|\")}")
    String[] getDeletedCasrn();

    @Value("#{target.OtherSynonyms == null? null : target.OtherSynonyms.split(\"\\|\")}")
    String[] getOther();

    @Value("#{target.BeilsteinSynonyms == null? null : target.BeilsteinSynonyms.split(\"\\|\")}")
    String[] getBeilstein();

    @Value("#{target.AlternateSynonyms == null? null : target.AlternateSynonyms.split(\"\\|\")}")
    String[] getAlternateCasrn();

    @JsonIgnore
    String getValidSynonym();

    @JsonIgnore
    String getGoodSynonyms();

    @JsonIgnore
    String getDeleteSynonyms();

    @JsonIgnore
    String getOtherSynonyms();

    @JsonIgnore
    String getBeilsteinSynonyms();

    @JsonIgnore
    String getAlternateSynonyms();


}