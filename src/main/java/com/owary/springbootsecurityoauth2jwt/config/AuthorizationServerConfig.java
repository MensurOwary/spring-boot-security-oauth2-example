package com.owary.springbootsecurityoauth2jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID                       = "owary-client"; // client id
    private static final String CLIENT_SECRET                   = new BCryptPasswordEncoder().encode("owary-secret");   //"owary-secret"; // secret which is going to be used in both sides that is authorization server and client
    private static final String GRANT_TYPE_PASSWORD             = "password";     // grant access by password
    private static final String AUTHORIZATION_CODE              = "authorization_code";
    private static final String REFRESH_TOKEN                   = "refresh_token";
    private static final String IMPLICIT                        = "implicit";
    private static final String SCOPE_READ                      = "read";
    private static final String SCOPE_WRITE                     = "write";
    private static final String TRUST                           = "trust";
    private static final int    ACCESS_TOKEN_VALIDITY_SECONDS   = 3600;
    private static final int    REFRESH_TOKEN_VALIDITY_SECONDS  = 6*60*60;

    private TokenStore tokenStore;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationServerConfig(TokenStore tokenStore, AuthenticationManager authenticationManager){
        this.tokenStore = tokenStore;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                // the client id
                .withClient(CLIENT_ID)
                // the secret, if any
                .secret(CLIENT_SECRET)
                // grant types
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
                // what the scopes are, if none then user won't be limited to any scope
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                // validity of the token
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                // refresh token validity
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // persistence of tokens
                .tokenStore(tokenStore)
                // to support passwords as well
                .authenticationManager(authenticationManager);
    }
}
