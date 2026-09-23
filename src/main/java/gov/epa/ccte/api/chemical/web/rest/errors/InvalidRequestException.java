package gov.epa.ccte.api.chemical.web.rest.errors;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
