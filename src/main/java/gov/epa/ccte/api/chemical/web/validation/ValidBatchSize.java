package gov.epa.ccte.api.chemical.web.validation;

import gov.epa.ccte.api.chemical.web.rest.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BatchSizeValidator.class)
public @interface ValidBatchSize {
    String message() default Messages.INVALID_BATCH_SIZE_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
