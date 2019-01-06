package pl.azurix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROOT_PASSWORD = "admin";
    public static final String ROOT_LOGIN = "admin";
    public static final String USER_PASSWORD = "user";
    public static final String USER_LOGIN = "password";

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch ( ServletException e ) {
            System.out.println("error with login: " + e);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(USER_LOGIN).password("{noop}" + USER_PASSWORD).roles("USER")
                .and()
                .withUser(ROOT_LOGIN).password("{noop}" + ROOT_PASSWORD).roles("USER", "ROOT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/*").permitAll()
                .antMatchers("/root/*").hasRole("ROOT")
                .anyRequest().hasRole("USER")
                .and().formLogin().loginPage("/auth/login");

    }
}