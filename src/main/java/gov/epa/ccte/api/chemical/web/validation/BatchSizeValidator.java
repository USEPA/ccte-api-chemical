package gov.epa.ccte.api.chemical.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Value;

import gov.epa.ccte.api.chemical.web.rest.Messages;

public class BatchSizeValidator implements ConstraintValidator<ValidBatchSize, Collection<?>> {

    @Value("${application.batch-size:200}")
    private int maxBatchSize;

    @Override
    public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {

        if (collection == null || collection.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.EMPTY_BATCH_MSG)
                    .addConstraintViolation();
            return false;
        }

        if (collection.size() > maxBatchSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.MAX_BATCH_SIZE_MSG.formatted(maxBatchSize))
            .addConstraintViolation();
            return false;
        }

        return true;

    }

}
