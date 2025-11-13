package gov.epa.ccte.api.chemical.projection;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalSynonym} entity
 */
@JsonPropertyOrder({
    "dtxsid",
    "valid",
    "good",
    "other",
    "deleted",
    "beilstein",
    "alternate",
    "pcCode"
})
@Schema(name = "ChemicalSynonymAll", description = "Different types of synonyms for request dtxsid")
public interface ChemicalSynonymAll {

    String getDtxsid();
    @Value("#{target.ValidSynonym == null? null : target.ValidSynonym.split('\\|')}")
    String[] getValid();
    @Value("#{target.GoodSynonyms == null? null : target.GoodSynonyms.split('\\|')}")
    String[] getGood();
    @Value("#{target.DeletedSynonyms == null? null : target.DeletedSynonyms.split('\\|')}")
    String[] getOther();
    @Value("#{target.BeilsteinSynonyms == null? null : target.BeilsteinSynonyms.split('\\|')}")
    String[] getDeleted();
    @Value("#{target.OtherSynonyms == null? null : target.OtherSynonyms.split('\\|')}")
    String[] getBeilstein();
    @Value("#{target.AlternateSynonyms == null? null : target.AlternateSynonyms.split('\\|')}")
    String[] getAlternate();
    @Value("#{target.PcCode == null? null : target.PcCode.split('\\|')}")
    String[] getPcCode();

}