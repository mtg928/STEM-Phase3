package uk.co.topfieldconsultancy.stem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uk.co.topfieldconsultancy.stem.domain.StemConfig;

@SpringBootApplication
@EnableConfigurationProperties(StemConfig.class)

public class StemApplication {


    public static void main(String[] args) {
        SpringApplication.run(StemApplication.class, args);
    }

}
