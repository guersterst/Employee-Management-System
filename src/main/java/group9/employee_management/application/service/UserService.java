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
public class UserService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    /**
     * Fill database with sample users.
     */
    @Override
    public void run(String... strings) throws IOException {

        // 1.1.2022
        Date validityDate = new Date(1640991600000L);
        //TODO hashToken
        //TODO first and lastName
        User user1 = new User("1", "H.P.Baxxter", hashPassword("h0wmUchisthef1sh"), "13",
                true, false, "Lead singer", validityDate);

        User user2 = new User("2", "Farin Urlaub", hashPassword("abc123def"), "12",
                false, true, "Lead singer", validityDate);

        User user3 = new User("3", "Kristoffer Jonas Klauß", hashPassword("überallAnJederWand"),
                "13", false, false, "Rapper", validityDate);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    private String hashPassword(String password) {
        return encoder.encode(password);
    }

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
