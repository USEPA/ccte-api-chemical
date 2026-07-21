package gov.epa.ccte.api.chemical.web.rest.errors;

public class UnparseableSmilesException extends RuntimeException {

    public UnparseableSmilesException(String smilesString, Throwable reason) {
        super(String.format("Couldn't parse provided SMILES string '%s' - %s", smilesString, reason.getMessage()));
    }
}
