package gov.epa.ccte.api.chemical.web.rest.errors;

public class IdentifierNotFoundException extends RuntimeException{
    public IdentifierNotFoundException(String idType, String idValue) {
        super(String.format("%s with value %s not found.", idType, idValue));
    }
}
