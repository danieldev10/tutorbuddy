package ng.edu.mautech.tutorbuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurityConfig.class);

        // This class is responsible for configuring security settings for the
        // application.
        // It is marked as a Spring configuration class using the @Configuration
        // annotation.
        // It also enables web security with the @EnableWebSecurity annotation.

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                logger.info("Configuring HTTP Security");
                http
                                .csrf(csrf -> csrf.disable()) // Disables CSRF protection
                                .authorizeRequests(requests -> requests
                                                .antMatchers("/login", "/register").permitAll() // Permits access to
                                                                                                // these URLs without
                                                                                                // authentication
                                                .antMatchers("/admin/**").hasAuthority("admin") // Requires "admin"
                                                                                                // authority for URLs
                                                                                                // starting with
                                                                                                // "/admin/"
                                                .antMatchers("/tutor/**").hasAuthority("tutor") // Requires "tutor"
                                                                                                // authority for URLs
                                                                                                // starting with
                                                                                                // "/tutor/"
                                                .antMatchers("/student/**").hasAuthority("student") // Requires
                                                                                                    // "student"
                                                                                                    // authority for
                                                                                                    // URLs starting
                                                                                                    // with "/student/"
                                                .anyRequest().authenticated()) // Requires authentication for any other
                                                                               // URL
                                .formLogin(login -> login
                                                .loginPage("/login").permitAll() // Specifies the login page and allows
                                                                                 // access without authentication
                                                .successHandler(customAuthenticationSuccessHandler)) // Specifies a
                                                                                                     // custom success
                                                                                                     // handler
                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/accessDenied")) // Sets the access denied page
                                .logout(logout -> logout
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                                .logoutSuccessUrl("/login").permitAll()); // Configures logout settings
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                // This method creates and configures a BCryptPasswordEncoder bean.
                // BCryptPasswordEncoder is used for securely hashing and checking passwords.
                // It's a standard practice for storing passwords securely in the database.
                return new BCryptPasswordEncoder();
        }

        // The @Autowired annotation injects the UserDetailsService bean into this
        // class.
        // UserDetailsService is an interface used to load user-specific data.
        // It is typically implemented to load user details from a database or other
        // data source.

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public AuthenticationProvider authenticationProvider() {
                // This method creates and configures an AuthenticationProvider bean.
                // AuthenticationProvider is a Spring Security component responsible for user
                // authentication.
                // It uses a UserDetailsService to retrieve user details and a password encoder
                // for authentication.
                // In this case, it uses the userDetailsService bean and the
                // bCryptPasswordEncoder bean.
                // It's a crucial part of the authentication process in Spring Security.
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userDetailsService);
                provider.setPasswordEncoder(bCryptPasswordEncoder());
                return provider;
        }
}
