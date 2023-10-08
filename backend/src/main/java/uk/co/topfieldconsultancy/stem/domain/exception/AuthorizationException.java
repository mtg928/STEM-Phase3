package uk.co.topfieldconsultancy.stem.domain.exception;

public class AuthorizationException extends IllegalArgumentException{
    public AuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}
