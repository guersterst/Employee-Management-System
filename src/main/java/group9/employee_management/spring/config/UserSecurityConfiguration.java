package group9.employee_management.spring.config;

import group9.employee_management.application.Roles;
import group9.employee_management.application.service.MyUserDetailsService;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@EnableWebSecurity
@Configuration
public class UserSecurityConfiguration {

    private final UserRepository userRepository;

    /**
     * Sets the userRepository.
     *
     * @param userRepository the UserRepository to use.
     */
    @Autowired
    public UserSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider());
        return http
                .requiresChannel(channel ->
                        channel.anyRequest().requiresSecure())
                .authorizeRequests().antMatchers("/account/**", "/my-session/**", "/my-history/**", "/h2-console/**")
                .hasAnyRole(Roles.ADMIN.toString(), Roles.USER.toString())
                .and().authorizeRequests().antMatchers("/admin/**")
                .hasRole(Roles.ADMIN.toString())
                .and().formLogin().loginPage("/login").successHandler(myAuthenticationSuccessHandler())
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .and().authorizeRequests().antMatchers("/css/**", "/js/**", "/resources/**", "/static/**", "/webjars/**", "/h2-console"
                        + "/**").permitAll()
                .and().build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyUrlAuthenticationSuccessHandler();
    }

    /**
     * Returns a new Instance of MyUserDetailsService
     *
     * @return as described above.
     */
    @Bean
    public MyUserDetailsService userDetailsService() {
        return new MyUserDetailsService(userRepository);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bcryptEncoder());
        return authProvider;
    }
}
/*
8. User Manual
6. Mapping after login -> start session if last session has ended.
 */
