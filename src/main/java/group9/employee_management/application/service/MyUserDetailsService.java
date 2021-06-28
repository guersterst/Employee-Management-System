package group9.employee_management.application.service;

import group9.employee_management.application.Roles;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.spring.config.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

/**
 * A service to provide UserDetails.
 */
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getById(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRoles().stream().map(Roles::toString).toArray(String[]::new));
        return builder.build();

        // Alternative with a custom implementation of an UserDetail-class.
        /*
        return new MyUserDetails(user);
        return user.map(MyUserDetails::new).get();

         */
    }
}
