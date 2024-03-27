package gov.epa.ccte.api.chemical.web.rest.errors;

public class TypeValueNotFoundException extends RuntimeException{

    public TypeValueNotFoundException(String typeName, String typeValue) {
        super(String.format("%s with value %s not found.", typeName, typeValue));
    }
}
