package uk.co.topfieldconsultancy.stem.infrastructure.web;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.topfieldconsultancy.stem.application.AuthenticationApplication;

@RestController
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationApplication authenticationApplication;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

        boolean loggedIn = authenticationApplication.login(loginRequest);

        return loggedIn ?
                ResponseEntity.ok(LoginResponse.builder().success(true).access_token("access_token").build()) :
                ResponseEntity.badRequest().body(ErrorResponse.builder().error("login failed").error_message("login_failed").build());
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {

        boolean registered = authenticationApplication.register(registerRequest);
        return registered ?
                ResponseEntity.ok(new RegisterResponse()) :
                ResponseEntity.badRequest().body(ErrorResponse.builder().error("registration failed").error_message("registration_failed").build());
    }

    @RequestMapping(value = "/auth/resetPassword", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPassword) {

        boolean passwordReseted = authenticationApplication.resetPassword(resetPassword);
        return passwordReseted ?
                ResponseEntity.ok(new ResetPasswordResponse()) :
                ResponseEntity.badRequest().body(ErrorResponse.builder().error("reset password failed").error_message("reset password failed").build());
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class LoginResponse {
        private boolean success = true;
        private String access_token;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class RegisterRequest {
        private String email;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class RegisterResponse {
        private boolean success = true;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class ErrorResponse {
        private boolean success = false;
        private String error;
        private String error_message;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class ResetPasswordRequest {
        private String email;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class ResetPasswordResponse {
        private boolean success = true;
    }
}
