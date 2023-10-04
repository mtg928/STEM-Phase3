package uk.co.topfieldconsultancy.stem.application;

import org.springframework.stereotype.Component;
import uk.co.topfieldconsultancy.stem.infrastructure.web.AuthenticationController;

@Component
public class AuthenticationApplication {

    public static final String TEST_EMAIL = "email@example.com";
    public static final String TEST_PW = "pw";
    public boolean login(AuthenticationController.LoginRequest loginRequest) {
        //TODO: check in the database for user
        return loginRequest.getEmail().equalsIgnoreCase(TEST_EMAIL) && loginRequest.getPassword().equalsIgnoreCase(TEST_PW);
    }

    public boolean register(AuthenticationController.RegisterRequest registerRequest) {
        //TODO: register a user
        // and send an email with a password
        return registerRequest.getEmail().equalsIgnoreCase(TEST_EMAIL);
    }

    public boolean resetPassword(AuthenticationController.ResetPasswordRequest resetPassword) {
        //TODO: send an email with a password
        return resetPassword.getEmail().equalsIgnoreCase(TEST_EMAIL);
    }
}
