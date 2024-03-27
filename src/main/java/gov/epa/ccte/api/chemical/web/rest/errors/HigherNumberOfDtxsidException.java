package gov.epa.ccte.api.chemical.web.rest.errors;


public class HigherNumberOfDtxsidException extends RuntimeException {

    public HigherNumberOfDtxsidException(Integer size, Integer maxSize) {
        super(String.format("System supports only '%s' dtxsid at one time, '%s' are submitted.",maxSize, size));
    }
}
