package gov.epa.ccte.api.chemical.web.rest.errors;


public class HigherNumberOfIdsException extends RuntimeException {

    public HigherNumberOfIdsException(Integer size, Integer maxSize, String idType) {
        super(String.format("System supports only '%s' '%s' at one time, '%s' are submitted.",maxSize, size, idType));
    }
}
