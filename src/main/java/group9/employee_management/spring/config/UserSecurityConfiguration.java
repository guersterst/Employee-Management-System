package group9.employee_management.spring.config;

import group9.employee_management.application.Roles;
import group9.employee_management.application.service.MyUserDetailsService;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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


    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MyUrlAuthenticationSuccessHandler();
    }

    /**
     * Allows us to embed the webjars and apply css to our templates/views.
     * This is otherwise blocked when using Spring Security.
     * @param
     */

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/resources/**", "/static/**","/webjars/**","/h2-console/**");
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
                .and().formLogin().loginPage("/login").successHandler(myAuthenticationSuccessHandler())
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout");


                //TODO FEHLERREPRODUKTION: diese Zeile auskommentieren
                //.defaultSuccessUrl("/my-session/latest");

        //TODO Debug default success url nullpionter for newly created user
        //TOOD remove newly created debug user



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

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bcryptEncoder());
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder(10));

        //TODO FEHLERREPRODUKTION: diesen Teil ent-kommentieren
        //auth.userDetailsService(userDetailsService()).passwordEncoder(getPasswordEncoder());

        //TODO FEHLERREPRODUKTION: diesen Teil auskommentieren
        //auth.userDetailsService(userDetailsService()).passwordEncoder(getPasswordEncoder());
        auth.authenticationProvider(authProvider());
    }
}
/*
3.Login first-time

5. COORDS: add to dto, add dto for coords, onSite -> setCoords, offSite -> delCoords.
(steffi koordinaten?)

7. DELETE From historyView -> index (userName) von steffi?
pathVariables

 */
