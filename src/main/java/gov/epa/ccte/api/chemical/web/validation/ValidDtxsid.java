package gov.epa.ccte.api.chemical.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import gov.epa.ccte.api.chemical.web.rest.Messages;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DtxsidValidator.class)
public @interface ValidDtxsid {

    String message() default Messages.BAD_DTXSID_FORMAT_MSG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
