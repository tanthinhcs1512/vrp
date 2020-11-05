package webserviceapi.model.response;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field."),
    RECORD_ALREADY_EXISTS("Record already exists."),
    INTERNAL_SERVER_ERROR("Interal server error."),
    EMAIL_NOT_FOUND("Email not found.");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
