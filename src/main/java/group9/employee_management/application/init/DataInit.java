package group9.employee_management.application.init;

import group9.employee_management.application.Roles;
import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;
    private final WorkSessionRepository workSessionRepository;

    @Autowired
    public DataInit(EmployeeRepository employeeRepository, WorkSessionRepository workSessionRepository,
                    UserRepository userRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
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

        Employee employee1 = new Employee("student","H.P.","Baxxter", hashPassword("student"),
                true, "Lead singer", validityDate, workSessions);
        employee1.setFirstLogin(false);
        Employee employee2 = new Employee("url01","Farin", "Urlaub", hashPassword("abc123def"),
                false, "Lead singer", validityDate, workSessions);
        employee2.setFirstLogin(false);
        Employee employee3 = new Employee("kla01","Kristoffer Jonas", "Klauß", hashPassword("überallAnJederWand"),
                false, "Rapper", validityDate, workSessions);


        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);


        WorkSession session1 = new WorkSession(0, new Date(1624354267000L), new Date(1624654267000L), "First "
                + "session"
                , false, true, employee1);

        WorkSession session2 = new WorkSession(1, new Date(1624354366000L), null, "Second session"
                , false, true, employee1);

        WorkSession session3 = new WorkSession(0, new Date(1624354366000L), null, "My first session"
                , true, true, employee2);

        WorkSession session4 = new WorkSession(1, new Date(1624354366000L), null, "My second "
                + "session"
                , true, true, employee2);

        workSessionRepository.save(session1);
        workSessionRepository.save(session2);
        workSessionRepository.save(session3);
        workSessionRepository.save(session4);

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        // Create Users: Better to work with services in general
        userRepository.save(new User("admin", hashPassword("admin"), null, Roles.ADMIN));
        userRepository.save(new User("student", "student", employee1, Roles.USER));
    }
}
