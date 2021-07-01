package group9.employee_management.application.init;

import group9.employee_management.application.Roles;
import group9.employee_management.application.service.AccountService;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.EmployeeRepository;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Collections;
import java.util.Set;

@Component
public class DataInit implements CommandLineRunner {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkSessionRepository workSessionRepository;
    private final AccountService accountService;
    private final WorkSessionService workSessionService;


    @Autowired
    public DataInit(EmployeeRepository employeeRepository, WorkSessionRepository workSessionRepository,
                    UserRepository userRepository, AccountService accountService, WorkSessionService workSessionService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.workSessionRepository = workSessionRepository;
        this.accountService = accountService;
        this.workSessionService = workSessionService;
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
        Employee turing = accountService.createUser("student","Alan", "Turing",
                "student", false, "Researcher");
        Employee linus = accountService.createUser("linus","Linus", "Torvald",
                "linux", false, "Chief code magician");
        Employee scooter = accountService.createUser("hpb","H.P.", "Baxxter",
                "hpb123", false, "Lead singer");

        userRepository.save(new User("admin", hashPassword("admin"), null, Roles.ADMIN, Roles.USER));

        workSessionService.startSession("student", "Creating turing machine", true, true);
        workSessionService.stopSession("student");
        workSessionService.startSession("student", "Proving p=np", true, true);
        workSessionService.stopSession("student");
        workSessionService.startSession("student", "Important meeting", false, true);

        workSessionService.startSession("linus", "Coding", true, true);
        workSessionService.stopSession("linus");

        workSessionService.startSession("hpb", "Songwriting", true, true);
        workSessionService.stopSession("hpb");
        workSessionService.startSession("hpb", "Concert", false, true);
    }
}
