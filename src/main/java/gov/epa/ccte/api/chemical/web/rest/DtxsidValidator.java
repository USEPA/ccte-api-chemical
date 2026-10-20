package gov.epa.ccte.api.chemical.web.rest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Locale;

public class DtxsidValidator implements ConstraintValidator<ValidDTXSID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        final boolean isValid = value.toUpperCase(Locale.US).matches("^DTXSID\\d+$");

        return isValid;
    }

}
