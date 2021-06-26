package group9.employee_management.spring.config;

import group9.employee_management.application.Roles;
import group9.employee_management.application.service.MyUserDetailsService;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

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

    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
 */

    /**
     * Allows us to embed the webjars and apply css to our templates/views.
     * This is otherwise blocked when using Spring Security.
     * @param
     */
    /*
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**");
        web.ignoring().antMatchers("/css/**", "/js/**", "/resources/**", "/static/**","/webjars/**");
    }
     */



    //TODO not for production
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
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


    /**
     * Configure the login. Utilized to use our custom login page.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/account/**", "/my-session")
                .hasAnyRole(Roles.ADMIN.toString(), Roles.USER.toString())
                .and().authorizeRequests().antMatchers("/admin/**")
                .hasRole(Roles.ADMIN.toString())
                .and().formLogin();



/*
        http.authorizeRequests().regexMatchers("/user").authenticated()
                .anyRequest().hasAnyRole(Roles.USER.toString(), Roles.ADMIN.toString())
                .and()
                .formLogin();

 */


        //TODO see to it, that admin is redirected differently

        //TODO if you want to be able to login atm use (student, student) and this:

        http.authorizeRequests().regexMatchers("/user").authenticated()
                .anyRequest().hasAnyRole(Roles.USER.toString(), Roles.ADMIN.toString())
                .and()
                .formLogin();



                /*
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/user",true)
                //.defaultSuccessUrl("/my-session", true)
                .and()
                .logout().permitAll()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login");
>>>>>>> 7d3cda4a986d757cbe88e070db3b750881e9fc24

                 */
    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder(10));
        //auth.userDetailsService(userDetailsService()).passwordEncoder(getPasswordEncoder());
        auth.userDetailsService(userDetailsService());
    }
}

/*

HEUTE
1. User creation D1
2. Auth zuweisen

MORGEN

2. Email Weissgerber
6.foreign_key bug

3.Login first-time
5.password encryption
 */
