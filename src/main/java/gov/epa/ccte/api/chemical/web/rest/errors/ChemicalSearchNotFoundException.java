package gov.epa.ccte.api.chemical.web.rest.errors;

import java.util.List;

public class ChemicalSearchNotFoundException extends RuntimeException{

    List<String> errorMsgs;
    List<String> suggestions;
    public ChemicalSearchNotFoundException(List<String> errorMsgs, List<String> suggestions) {
        this.errorMsgs = errorMsgs;
        this.suggestions = suggestions;
    }

    List<String> getErrorMsgs(){
        return errorMsgs;
    }

    List<String> getSuggestions(){
        return suggestions;
    }
}
