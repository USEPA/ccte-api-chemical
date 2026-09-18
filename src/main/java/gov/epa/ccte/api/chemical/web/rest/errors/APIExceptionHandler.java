package gov.epa.ccte.api.chemical.web.rest.errors;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import gov.epa.ccte.api.chemical.web.rest.Messages;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HigherNumberOfIdsException.class)
    ProblemDetail handleHigherNumberOfDtxsidException(HigherNumberOfIdsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IdentifierNotFoundException.class)
    ProblemDetail handleIdentifierNotFoundException(IdentifierNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TypeValueNotFoundException.class)
    ProblemDetail handleTypeValueNotFoundException(TypeValueNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ChemicalSearchNotFoundException.class)
    ProblemDetail handleChemicalSearchNotFoundException(ChemicalSearchNotFoundException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                String.join("\n", ex.getErrorMsgs()));
        problemDetail.setProperty("suggestions", ex.getSuggestions());

        return problemDetail;
    }

    @ExceptionHandler(UnparseableSmilesException.class)
    ProblemDetail handleUnparseableSmilesExceptions(UnparseableSmilesException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid SMILES string");
        return problemDetail;
    }

    @ExceptionHandler(InvalidRequestException.class)
    ProblemDetail handleInvalidRequestException(InvalidRequestException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Constraint Violations");
        problemDetail.setProperty("violations", extractValidationErrors(ex));

        return ResponseEntity.badRequest().body(problemDetail);

        // return ResponseEntity.badRequest();
        // return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    private Map<String, String> extractValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            }
        });
        return errors;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(Messages.INVALID_REQUEST_BODY_MSG);

        String detail = Messages.MALFORMED_REQUEST_BODY_MSG;
        if (ex.getMessage() != null && ex.getMessage().contains("Required request body is missing")) {
            detail = Messages.REQUEST_BODY_MUST_NOT_BE_EMPTY_MSG;
        }

        problemDetail.setDetail(detail);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(InvalidBatchMsReadyRequestException.class)
    ProblemDetail handleInvalidBatchMsReadyRequestException(InvalidBatchMsReadyRequestException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {

        final var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                Messages.REQUEST_BODY_FAILED_VALIDATION_MSG);
        problem.setTitle(Messages.VALIDATION_FAILURE_MSG);

        final var errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .toList();

        problem.setProperty("violations", errors);

        return problem;
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(Messages.VALIDATION_FAILURE_MSG);
        problem.setDetail(Messages.VALIDATION_FAILURE_MSG);

        var errors = ex.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        problem.setProperty("violations", errors);

        return ResponseEntity.badRequest().body(problem);
    }

}
