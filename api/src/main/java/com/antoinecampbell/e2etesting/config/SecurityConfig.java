package com.antoinecampbell.e2etesting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

/**
 * Security configurations
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @EnableWebSecurity
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final DataSource dataSource;
        private final PasswordEncoder passwordEncoder;

        @Autowired
        public WebSecurityConfig(@Qualifier("dataSource") DataSource dataSource,
                                 PasswordEncoder passwordEncoder) {
            this.dataSource = dataSource;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.setMaxAge(3600L);
            source.registerCorsConfiguration("/**", config);

            http.csrf().disable()
                    .cors().configurationSource(source)
                    .and()
                    .httpBasic()
                    .and()
                    .authorizeRequests().antMatchers(HttpMethod.POST, "/users", "/users/").anonymous()
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .logout().logoutUrl("/logout");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsManager())
                    .passwordEncoder(passwordEncoder);
        }

        @Bean
        public UserDetailsManager userDetailsManager() {
            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
            userDetailsManager.setDataSource(dataSource);
            return userDetailsManager;
        }

        @Bean
        public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
            return new SecurityEvaluationContextExtension();
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public static class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    }

}
