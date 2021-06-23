package group9.employee_management.application.init;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Collections;
import java.util.Set;

@Component
public class DataInit implements CommandLineRunner {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final WorkSessionRepository workSessionRepository;

    @Autowired
    public DataInit(UserRepository userRepository, WorkSessionRepository workSessionRepository) {
        this.userRepository = userRepository;
        this.workSessionRepository = workSessionRepository;
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

        // An empty set of work-sessions.
        Set<WorkSession> workSessions = Collections.emptySet();

        User user1 = new User("bax01","H.P.","Baxxter", hashPassword("h0wmUchisthef1sh"),
                true, "Lead singer", validityDate, workSessions);
        user1.setFirstLogin(false);
        User user2 = new User("url01","Farin", "Urlaub", hashPassword("abc123def"),
                false, "Lead singer", validityDate, workSessions);
        user2.setFirstLogin(false);
        User user3 = new User("kla01","Kristoffer Jonas", "Klauß", hashPassword("überallAnJederWand"),
                false, "Rapper", validityDate, workSessions);


        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        WorkSession session1 = new WorkSession(0, new Date(1624354267000L), new Date(1624354305000L), "First "
                + "session"
                , false, true, user1);


        WorkSession session2 = new WorkSession(1, new Date(1624354366000L), new Date(1624354376000L), "Second session"
                , false, true, user1);


        workSessionRepository.save(session1);
        workSessionRepository.save(session2);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
