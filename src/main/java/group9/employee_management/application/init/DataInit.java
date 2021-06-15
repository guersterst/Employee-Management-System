package group9.employee_management.application.init;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DataInit implements CommandLineRunner {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public DataInit(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder(10);
    }


    private String hashPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Fill database with sample users.
     */
    @Override
    public void run(String... strings) {

        // This date is equivalent to 1.1.2022.
        Date validityDate = new Date(1640991600000L);
        //TODO first and lastName
        User user1 = new User("bax01","H.P.","Baxxter", hashPassword("h0wmUchisthef1sh"),
                true, false, "Lead singer", validityDate);
        user1.setFirstLogin(false);
        User user2 = new User("url01","Farin", "Urlaub", hashPassword("abc123def"),
                false, true, "Lead singer", validityDate);
        user2.setFirstLogin(false);
        User user3 = new User("kla01","Kristoffer Jonas", "Klauß", hashPassword("überallAnJederWand"),
                false, false, "Rapper", validityDate);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

}
