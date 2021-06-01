package group9.employee_management.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * A service to interact with the user tables.
 */
@Service
public class UserService {

    @Autowired(required = false)
    private UserRepository userRepository;

    public User getUser(String id) //throws NoSuchElementException
    {
        return userRepository.findById(id).get(); //.orElseThrow();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteTopic(String id) {
        userRepository.deleteById(id);
    }
}
