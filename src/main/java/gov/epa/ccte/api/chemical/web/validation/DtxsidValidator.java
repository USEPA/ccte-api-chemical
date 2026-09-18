package gov.epa.ccte.api.chemical.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import gov.epa.ccte.api.chemical.web.rest.Messages;

public class DtxsidValidator implements ConstraintValidator<ValidDtxsid, String> {

    public static final Pattern DTXSID_FORMAT_REGEX = Pattern.compile("^DTXSID[0-9]+$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty() || value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.EMPTY_DTXSID_MSG)
                    .addConstraintViolation();
            return false;
        }

        return DTXSID_FORMAT_REGEX.matcher(value).matches();
    }

}
