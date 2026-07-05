package ma.bdcc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true)
                .permitAll()
            )
            .rememberMe(remember -> remember
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/h2-console/**").permitAll()
                .requestMatchers("/products/add/**", "/products/save/**",
                    "/products/edit/**", "/products/update/**").hasRole("ADMIN")
                .requestMatchers("/products/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN", "USER")
            .build();

        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("user123"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
