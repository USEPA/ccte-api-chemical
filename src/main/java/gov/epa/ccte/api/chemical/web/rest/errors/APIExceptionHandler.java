package gov.epa.ccte.api.chemical.web.rest.errors;

import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HigherNumberOfIdsException.class)
    ProblemDetail handleHigherNumberOfDtxsidException(HigherNumberOfIdsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IdentifierNotFoundException.class)
    ProblemDetail handleIdentifierNotFoundException(IdentifierNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TypeValueNotFoundException.class)
    ProblemDetail handleTypeValueNotFoundException(TypeValueNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ChemicalSearchNotFoundException.class)
    ProblemDetail handleChemicalSearchNotFoundException(ChemicalSearchNotFoundException ex){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                String.join("\n",ex.getErrorMsgs()));
        problemDetail.setProperty("suggestions", ex.getSuggestions());

        return problemDetail;
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex){
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
//                "Constraint Violation");
//        problemDetail.setProperty("violations", extractValidationErrors(ex));
//
//        return problemDetail;
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Constraint Violations");
        problemDetail.setProperty("violations", extractValidationErrors(ex));

        return ResponseEntity.badRequest().body(problemDetail);

        //return ResponseEntity.badRequest();
        //return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    private Map<String, String> extractValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            if(error instanceof FieldError){
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            }
        });
        return errors;
    }
}
