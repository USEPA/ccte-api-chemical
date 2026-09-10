package gov.epa.ccte.api.chemical.unit;

import gov.epa.ccte.api.chemical.web.rest.BatchMsReadyMassForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
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
        assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("Give Error value is null when validation is performed, then one violations is generated.")
    public void testFormWithNullError() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();
        form.setMasses(List.of(1.0, 2.0));

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        // both error and masses are null
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("Give Error value is null when validation is performed, then one violations is generated.")
    public void testFormWithNullMasses() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();
        form.setError(1);

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        // both error and masses are null
        assertThat(violations).hasSize(1);
    }
    
    @Test
    @DisplayName("Given masses is empty when validation is performed, then one violation is generated.")
    public void testFormWithEmptyMasses() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();
        form.setMasses(Collections.emptyList());
        form.setError(1);

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        assertThat(violations).hasSize(1);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
                .contains("Masses couldn't be empty");
    }

    @Test
    @DisplayName("Given masses contains null when validation is performed, then one violation is generated.")
    public void testFormWithNullMassElement() {
        BatchMsReadyMassForm form = new BatchMsReadyMassForm();
        form.setMasses(java.util.Arrays.asList(1.0, null));
        form.setError(1);

        Set<ConstraintViolation<BatchMsReadyMassForm>> violations = validator.validate(form);

        assertThat(violations).hasSize(1);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
                .contains("masses must not contain null values.");
    }

}
