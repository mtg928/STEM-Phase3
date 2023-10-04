package uk.co.topfieldconsultancy.stem.application;

import org.springframework.stereotype.Component;
import uk.co.topfieldconsultancy.stem.infrastructure.web.AuthenticationController;

@Component
public class AuthenticationApplication {
    public boolean login(AuthenticationController.LoginRequest loginRequest) {
        //TODO: check in the database for user
        return loginRequest.getEmail().equalsIgnoreCase("email") && loginRequest.getPassword().equalsIgnoreCase("pw");
    }

    public boolean register(AuthenticationController.RegisterRequest registerRequest) {
        //TODO: register a user
        // and send an email with a password
        return registerRequest.getEmail().equalsIgnoreCase("email");
    }

    public boolean resetPassword(AuthenticationController.ResetPasswordRequest resetPassword) {
        //TODO: send an email with a password
        return resetPassword.getEmail().equalsIgnoreCase("email");
    }
}
