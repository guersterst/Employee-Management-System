package group9.employee_management.spring.config;

import group9.employee_management.application.Roles;
import group9.employee_management.application.service.MyUserDetailsService;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

   // @Autowired
   // public UserSecurityConfiguration(UserRepository userRepository) {
   //     this.userRepository = userRepository;
   // }

    /**
     * Sets the userRepository.
     * @param userRepository the UserRepository to use.
     */
    @Autowired
    public UserSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider oathProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    /**
     * Returns a new Instance of MyUserDetailsService
     * @return as described above.
     */
    @Bean
    public MyUserDetailsService userDetailsService() {
        return new MyUserDetailsService(userRepository);
    }


    //TODO not for production
    //@Bean
    //public PasswordEncoder getPasswordEncoder() {
    //    return NoOpPasswordEncoder.getInstance();
   // }

    //@Bean
   // public UserDetailsService userDetailsService() {
    //    return new MyUserDetailsService(userRepository);
    //}

    /**
     * This fixes the issue of css not being applied to the login page.
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }


    /**
     * Configure the login.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().regexMatchers("/user").authenticated()
                .anyRequest().hasAnyRole(Roles.USER.toString(), Roles.ADMIN.toString())
                .and()
                .formLogin().loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/login?error=true")
                //.defaultSuccessUrl("/my-session", true)
                .and()
                .logout().permitAll()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login");
    }

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder(10));
    }

    /* Old code:
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Second tut
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/all").permitAll()
                .and().formLogin();


        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();
Funtkioniert:  http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/index.html?error=true");
        //http.authorizeRequests().antMatchers("/user/**").hasRole("user").antMatchers("/").hasRole("user");

        http.authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll().and().logout().permitAll().and().httpBasic();
    //hasRole("user").antMatchers("/").hasRole("user");

        //http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
         //   .and().formLogin().loginPage("/login").permitAll();
        http.authorizeRequests().regexMatchers("/user").authenticated()
                .anyRequest().hasAnyRole(Roles.USER.toString(), Roles.ADMIN.toString())
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login/authentication")
                .defaultSuccessUrl("/user", true)
                .failureUrl("/login?error=true");//.loginPage("/login").defaultSuccessUrl("/user", true);

        //TODO see to it, that afmin is redirected differently
    } */
}

