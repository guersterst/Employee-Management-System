package group9.employee_management.application.service;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;

/**
 * A service to interact with the user tables.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;


    //TODO clearer error messages than just false
    public boolean match(String password, String name) {
        if (userRepository.userExistsByName(name)) { ;
            String encodedPW = userRepository.findPasswordById(userRepository.findIdByName(name));
            return encoder.matches(password, encodedPW);
        } else {
            return false;
        }
    }

    /*
    Login getters.
     */
    public String getName(String id) {
        assert userRepository != null;
        return userRepository.findNameById(id);
    }

    public String getPassword(String id) {
        assert userRepository != null;
        return userRepository.findPasswordById(id);
    }

    public boolean isAdmin(String id) {
        assert userRepository != null;
        return userRepository.findIsAdminById(id);
    }

    public boolean isFirstLogin(String id) {
        assert userRepository != null;
        return userRepository.findIsFirstLoginById(id);
    }

    public String getToken(String id) {
        assert userRepository != null;
        return userRepository.findTokenById(id);
    }
}
