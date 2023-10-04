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
    public static final String RESET_PASSWORD_FAILED = "reset password failed";
    public static final String REGISTRATION_FAILED = "registration failed";
    public static final String LOGIN_FAILED = "login failed";
    public static final String ACCESS_TOKEN = "access_token";
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationApplication authenticationApplication;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

        boolean loggedIn = authenticationApplication.login(loginRequest);

        return loggedIn ?
                ResponseEntity.ok(LoginResponse.builder()
                        .success(true)
                        .access_token(ACCESS_TOKEN)
                        .build()) :
                ResponseEntity.badRequest()
                        .body(ErrorResponse.builder()
                                .error(LOGIN_FAILED)
                                .error_message(LOGIN_FAILED)
                                .build());
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {

        boolean registered = authenticationApplication.register(registerRequest);
        return registered ?
                ResponseEntity.ok(new RegisterResponse()) :
                ResponseEntity.badRequest()
                        .body(ErrorResponse.builder()
                                .error(REGISTRATION_FAILED)
                                .error_message(REGISTRATION_FAILED)
                                .build());
    }

    @RequestMapping(value = "/auth/resetPassword", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPassword) {

        boolean passwordReseted = authenticationApplication.resetPassword(resetPassword);
        return passwordReseted ?
                ResponseEntity.ok(new ResetPasswordResponse()) :
                ResponseEntity.badRequest()
                        .body(ErrorResponse.builder()
                                .error(RESET_PASSWORD_FAILED)
                                .error_message(RESET_PASSWORD_FAILED)
                                .build());
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
