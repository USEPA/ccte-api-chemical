package gov.epa.ccte.api.chemical.unit;

import gov.epa.ccte.api.chemical.service.ChemicalUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class ChemicalUtilsTest {

    String inchikey = "TZONNMJKAPGHSN-UHFFFAOYSA-N";

    @Test
    public void testInchikeyWithoutCharge(){
        String inchikeyWithoutCharge = ChemicalUtils.inchikeyWithoutCharge(inchikey);

        assertThat(inchikeyWithoutCharge).isEqualTo("TZONNMJKAPGHSN-UHFFFAOYSA");
    }

    @Test
    public void testInchikeyWithoutSecondlayer(){
        String inchikeyWithoutCharge = ChemicalUtils.inchikeyWithoutSecondlayer(inchikey);

        assertThat(inchikeyWithoutCharge).isEqualTo("TZONNMJKAPGHSN");
    }
    @Test
    public void testIsInchiKey(){
        Boolean result = ChemicalUtils.isInchiKey(inchikey);

        assertThat(result).isTrue();
    }
}
