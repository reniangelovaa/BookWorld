package bg.softuni.bookworld.config;

import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.service.session.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@ComponentScan
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests -> {
                                authorizeRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/", "/about-us", "/users/login", "/users/login-error", "/users/register").permitAll()
                                        .requestMatchers(HttpMethod.DELETE, "/api/cart/remove/**").authenticated()
                                        .anyRequest().authenticated();
                        }
                )
                .formLogin(formLogin -> {
                            formLogin
                                    .loginPage("/users/login")
                                    .usernameParameter("username")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/", true)
                                    .failureUrl("/users/login-error");
                        }
                )
                .logout(
                        logout -> {
                            logout
                                    .logoutUrl("/users/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true);
                        }
                )
                .build();
    }

    @Bean
    public AppUserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
}
