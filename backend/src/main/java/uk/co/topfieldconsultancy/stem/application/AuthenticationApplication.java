package uk.co.topfieldconsultancy.stem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
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
        User userFoundByEmail = userRepository.findByEmail(loginRequest.getEmail());
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16384, 8, 4, 32, 64);
        if (userFoundByEmail == null ||  !encoder.matches(loginRequest.getPassword(), userFoundByEmail.getHashedPassword())) {
            throw new IllegalArgumentException("User not found");
        }

        String jwt = jwtApplication.createJWT(userFoundByEmail.getEmail(), userFoundByEmail.getName(), userFoundByEmail.getRoles(), userFoundByEmail.getId());
        return jwt;
    }

    public boolean register(AuthenticationController.RegisterRequest registerRequest) {
        // TODO: and send an email with a password
        User userFoundByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (userFoundByEmail != null) {
            throw new IllegalArgumentException("Email already used!");
        }
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16384, 8, 4, 32, 64);
        var encodedPassword = encoder.encode(registerRequest.getPassword());
        userRepository.save(User.builder()
                .email(registerRequest.getEmail())
                .hashedPassword(encodedPassword)
                .roles(new String[]{"USER"})
                .activated(true)
                .build());

        emailApplication.sendRegistrationEmail(registerRequest.getEmail());
        return true;
    }

    public boolean resetPassword(AuthenticationController.ResetPasswordRequest resetPassword) {
        //TODO: send an email with a new password

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
