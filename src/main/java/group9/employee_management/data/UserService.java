package group9.employee_management.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * A service to interact with the user tables.
 */
@Service
public class UserService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    /**
     * Fill database with sample users.
     */
    @Override
    public void run(String... strings) {

        // 1.1.2022
        Date validityDate = new Date(1640991600000L);
        User user1 = new User("1","H.P. Baxxter", "h0wmUchisthef1sh", "13", true,
                false, "Lead singer", validityDate);
        User user2 = new User("2", "Farin Urlaub", "abc123def","12", false,
                true, "Lead singer", validityDate);
        User user3 = new User("3", "Kristoffer Jonas Klauß", "überallAnJederWand", "13", false, false, "Rapper",
                validityDate);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }


    public User getUser(String id) //throws NoSuchElementException
    {
        return userRepository.findById(id).get(); //.orElseThrow();
                                                     //.orElse(null);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteTopic(String id) {
        userRepository.deleteById(id);
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

}
