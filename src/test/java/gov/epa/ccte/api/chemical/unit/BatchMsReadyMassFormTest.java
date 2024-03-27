package gov.epa.ccte.api.chemical.unit;

import gov.epa.ccte.api.chemical.web.rest.BatchMsReadyMassForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BatchMsReadyMassFormTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    @DisplayName("Give default initialized form when validation is performed, then two violations are generated.")
    public void testFormInitializationWithEmptyConstructor() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        // both error and masses are null
        assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Give Error value is null when validation is performed, then one violations is generated.")
    public void testFormWithNullError() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();
        Double[] dummyValues = new Double[]{1.0,2.0};
        form.setMasses(dummyValues);

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        // both error and masses are null
        assertThat(violations.size()).isOne();
    }

    @Test
    @DisplayName("Give Error value is null when validation is performed, then one violations is generated.")
    public void testFormWithNullMasses() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();
        form.setError(1);

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        // both error and masses are null
        assertThat(violations.size()).isOne();
    }

}
