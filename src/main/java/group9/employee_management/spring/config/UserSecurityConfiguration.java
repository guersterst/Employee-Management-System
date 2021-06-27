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

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**");
        web.ignoring().antMatchers("/css/**", "/js/**", "/resources/**", "/static/**","/webjars/**","/h2-console/**");
    }




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

        http.authorizeRequests().antMatchers("/account/**", "/my-session/**", "/my-history/**","/h2-console/**")
                .hasAnyRole(Roles.ADMIN.toString(), Roles.USER.toString())
                .and().authorizeRequests().antMatchers("/admin/**")
                .hasRole(Roles.ADMIN.toString())
                .and().formLogin()

                //TODO FEHLERREPRODUKTION: diese Zeile auskommentieren
                .defaultSuccessUrl("/my-session/latest");
        http.csrf().disable();



                //TODO FEHLERREPRODUKTION: diesen Teil Kette anfügen
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

 */

    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder(10));

        //TODO FEHLERREPRODUKTION: diesen Teil ent-kommentieren
        //auth.userDetailsService(userDetailsService()).passwordEncoder(getPasswordEncoder());

        //TODO FEHLERREPRODUKTION: diesen Teil auskommentieren
        auth.userDetailsService(userDetailsService());
    }
}

/*
3.Login first-time
4. What if there is no session? -> Worksessioncontroller returns dto filled with null
or what if is admin and no associated employee
5. doc.
6. getThree

5. password encryption

6. On-demand: improve controllers
 */
