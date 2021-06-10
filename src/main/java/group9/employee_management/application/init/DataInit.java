package group9.employee_management.application.init;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.Date;

public class DataInit implements CommandLineRunner {


    @Autowired
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    UserRepository userRepository;


    private String hashPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Fill database with sample users.
     */
    @Override
    public void run(String... strings) throws IOException {

        // 1.1.2022
        Date validityDate = new Date(1640991600000L);
        //TODO hashToken
        //TODO first and lastName
        User user1 = new User("H.P.Baxxter", hashPassword("h0wmUchisthef1sh"), "13",
                true, false, "Lead singer", validityDate);

        User user2 = new User("Farin Urlaub", hashPassword("abc123def"), "12",
                false, true, "Lead singer", validityDate);

        User user3 = new User("Kristoffer Jonas Klauß", hashPassword("überallAnJederWand"),
                "13", false, false, "Rapper", validityDate);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

}
