package uk.co.topfieldconsultancy.stem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "stem")
@Configuration
@Getter
@Setter
public class StemConfig {

    private String signingKey;

}