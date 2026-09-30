package gov.epa.ccte.api.chemical.web.rest;

import jakarta.validation.ConstraintValidatorContext;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

public class DtxsidValidatorTests {

    private DtxsidValidator validator;
    private ConstraintValidatorContext context;
    
    @BeforeEach
    private void setupEach() {
        validator = new DtxsidValidator();
        context = mock(ConstraintValidatorContext.class);
    }
    
    @Test
    public void givenValidDTXSID_shouldBeValid() throws Exception {
        // given a good DTXSID
        var dtxsid = "DTXSID7020182";
        
        // when validating that DTXSID
        boolean isValid = validator.isValid(dtxsid, context);
        
        // then should be valid
        assertThat(isValid).isTrue();
    }
    
    @Test
    public void givenNullDTXSID_shouldNotBeValid() throws Exception {
        // given a null DTXSID
        var dtxsid = (String) null;
        
        // when validating that DTXSID
        boolean isValid = validator.isValid(dtxsid, context);
        
        // then should not be valid
        assertThat(isValid).isFalse();
    }
    
    @Test
    public void givenDTXSIDWithEmbeddedUnicodeNull_shouldNotBeValid() throws Exception {
        // given a DTXSID with embedded unicode null
        var dtxsid = "DTXSID\u00007020182";
        
        // when validating that DTXSID
        boolean isValid = validator.isValid(dtxsid, context);
        
        // then should not be valid
        assertThat(isValid).isFalse();
    }

    @Test
    public void givenEmptyDTXSIDString_shouldNotBeValid() throws Exception {
        // given an empty DTXSID
        var dtxsid = "";
        
        // when validating that DTXSID
        boolean isValid = validator.isValid(dtxsid, context);
        
        // then should not be valid
        assertThat(isValid).isFalse();
    }

    @Test
    public void givenRandomDTXSIDString_shouldNotBeValid() throws Exception {
        // given an random String as DTXSID
        var dtxsid = "random-value=4";
        
        // when validating that DTXSID
        boolean isValid = validator.isValid(dtxsid, context);
        
        // then should not be valid
        assertThat(isValid).isFalse();
    }
}
