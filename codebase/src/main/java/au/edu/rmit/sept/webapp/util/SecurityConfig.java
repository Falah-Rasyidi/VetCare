package au.edu.rmit.sept.webapp.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    AntPathRequestMatcher[] WHITE_LIST = new AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/**"),
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/login**"),
    };

    @Autowired
    AuthenticationSuccessHandler been;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession session) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/"))
                        .permitAll());
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(PathRequest.toH2Console())
                        .permitAll()
                        .requestMatchers(WHITE_LIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .headers(
                        headers -> headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.oauth2Login(Customizer.withDefaults());
        http
                .oauth2Login(oauth2->oauth2
//                        .loginPage("/login")
                        .successHandler(been)

                );
        return http.build();
    }

}

