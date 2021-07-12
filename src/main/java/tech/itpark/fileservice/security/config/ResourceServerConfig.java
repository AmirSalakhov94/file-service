package tech.itpark.fileservice.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RequiredArgsConstructor
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public DefaultTokenServices tokenService() {
        DefaultTokenServices userInfoTokenServices
                = new DefaultTokenServices();
        userInfoTokenServices.setTokenStore(tokenStore());
        return userInfoTokenServices;
    }
}
