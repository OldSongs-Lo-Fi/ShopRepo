package shop.template.onlineShop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests

                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()

                        // START POSITION
                        .requestMatchers("/orders/**").hasAnyRole("ADMIN","MODERATOR", "CUSTOMER")
                        .requestMatchers("/comments/**").hasAnyRole("ADMIN","MODERATOR", "CUSTOMER")
                        .requestMatchers("/products/**").hasAnyRole("ADMIN","MODERATOR", "CUSTOMER")
                        .requestMatchers("/users/**").hasAnyRole("ADMIN","MODERATOR", "CUSTOMER")

                        //USERS

                        .requestMatchers("/users/sendConfirmationMessage").permitAll()
                        .requestMatchers("/users/confirmEmail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/changeEmail").permitAll()

                        .requestMatchers(HttpMethod.GET, "/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/*/ban").hasAnyRole("ADMIN", "MODERATOR")

                        //COMMENTS
                        .requestMatchers(HttpMethod.DELETE, "/comments/*").hasAnyRole("ADMIN", "MODERATOR")

                        //ORDERS
                        .requestMatchers(HttpMethod.PATCH, "/orders/*/status").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers(HttpMethod.GET, "/orders/").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers(HttpMethod.GET, "/orders/*").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers(HttpMethod.GET, "/orders/byDate").hasAnyRole("ADMIN", "MODERATOR")

                        //PRODUCTS
                        .requestMatchers(HttpMethod.POST, "/products/").hasAnyRole("ADMIN", "MODERATOR")

                        .requestMatchers(HttpMethod.GET, "/products/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/*/comments").permitAll()

                        .requestMatchers(HttpMethod.DELETE, "/products/*").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers(HttpMethod.PATCH, "/products/*/available").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers(HttpMethod.PUT, "/products/*").hasAnyRole("ADMIN", "MODERATOR")


                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(httpSecurityCsrfConfigurer -> {})
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
