package pl.azurix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


@EnableAuthorizationServer
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}


/*
 * https://dzone.com/articles/secure-spring-rest-with-spring-security-and-oauth2
 *
 * TODO: for ex. @Transactional(readOnly = true)
 * TODO: for ex. @PreAuthorize("hasAuthority('COMPANY_READ') and hasAuthority('DEPARTMENT_READ')")
 */