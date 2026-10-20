package gov.epa.ccte.api.chemical.web.rest;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DtxsidValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDTXSID {

    String message() default "Invalid DTXSID format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
