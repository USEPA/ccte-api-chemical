package gov.epa.ccte.api.chemical.web.rest;

public class Messages {


    private Messages() {
        throw new UnsupportedOperationException("pure static class; don't create");
    }

    public static final String VALIDATION_FAILURE_MSG = "Validation failure";
    
    public static final String INVALID_REQUEST_BODY_MSG = "Invalid request body";
    public static final String MALFORMED_REQUEST_BODY_MSG = "Request body is malformed.";
    public static final String REQUEST_BODY_FAILED_VALIDATION_MSG = "Request body failed validation";
    public static final String REQUEST_BODY_MUST_NOT_BE_EMPTY_MSG = "Request body must not be empty";

    // batch size messages
    public static final String EMPTY_BATCH_MSG = "Batch must not be empty";
    public static final String INVALID_BATCH_SIZE_MSG = "Invalid batch size";

    // dtxsid format messages
    public static final String EMPTY_DTXSID_MSG = "DTXSID cannot be empty";
    public static final String BAD_DTXSID_FORMAT_MSG = "Bad DTXSID format";

    // dtxcid format messages
    public static final String EMPTY_DTXCID_MSG = "DTXCID cannot be empty";
    public static final String BAD_DTXCID_FORMAT_MSG = "Bad DTXCID format";

    // special cases :(
    public static final String MAX_BATCH_SIZE_MSG = "Batch exceeded maximum configured size (%d)";

}
