package uk.co.topfieldconsultancy.stem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.topfieldconsultancy.stem.domain.User;
import uk.co.topfieldconsultancy.stem.domain.UserRepository;
import uk.co.topfieldconsultancy.stem.infrastructure.web.AuthenticationController;

@Component
public class AuthenticationApplication {

    public static final String TEST_EMAIL = "email@example.com";
    public static final String TEST_PW = "pw";
    @Autowired
    JWTApplication jwtApplication;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailApplication emailApplication;

    public String login(AuthenticationController.LoginRequest loginRequest) {
        //TODO: check in the database for user and return access_token
        User userFoundByEmail = userRepository.findByEmail(loginRequest.getEmail());
        if (userFoundByEmail == null || !loginRequest.getPassword()
                .equalsIgnoreCase(userFoundByEmail.getHashedPassword())) {
            throw new IllegalArgumentException("User not found");
        }

        String jwt = jwtApplication.createJWT(userFoundByEmail.getRoles(), userFoundByEmail.getId());
        return jwt;
    }

    public boolean register(AuthenticationController.RegisterRequest registerRequest) {
        //TODO: register a user
        // and send an email with a password
        //TODO: hash password
        String generatedPassword = "generatedPassword";
        userRepository.save(User.builder()
                .email(registerRequest.getEmail())
                .hashedPassword(generatedPassword)
                .roles(new String[]{"USER"})
                .activated(true)
                .build());

        emailApplication.sendRegistrationEmail(registerRequest.getEmail());
        return true;
    }

    public boolean resetPassword(AuthenticationController.ResetPasswordRequest resetPassword) {
        //TODO: send an email with a password

        User userFoundByEmail = userRepository.findByEmail(resetPassword.getEmail());
        if (userFoundByEmail == null) {
            emailApplication.sendResetPasswordEmail(resetPassword.getEmail());
            return true;
        } else {
            return true;
        }
    }

    public Long extractUserIdFromAuthorizationHeader(String authorizationHeader) {
        String jwt = authorizationHeader.replace("Bearer ", "");
        return jwtApplication.verifyAndExtractUserId(jwt);
    }
}
