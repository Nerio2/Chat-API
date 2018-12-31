package pl.azurix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .jdbc(this.dataSource)
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }
}