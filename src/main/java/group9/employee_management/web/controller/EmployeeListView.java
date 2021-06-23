package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.WorkSessionDTO;
import group9.employee_management.web.dto.WorkSessionListEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeListView {

    //TODO defensiveness what if no session

    //AUTH ADMIN
    private final WorkSessionService workSessionService;

    @Autowired
    public EmployeeListView(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @GetMapping(
            value = "/{userName}/session"
    )
    @ResponseBody
    public String getLatestSessionAndUser(@PathVariable("userName") String userName) throws JsonProcessingException {

        WorkSessionListEntryDTO workSessionListEntryDTO =
                WorkSessionListEntryDTO.fromEntities(workSessionService.getUser(userName),
                        workSessionService.getLatest(userName));

        return workSessionListEntryDTO.toJSON();
    }

    //TODO test
    @GetMapping(
            value = "/working"
    )
    public String getUsersWithRunningSessions() {
        return workSessionService.getUsersWithRunningSessionsAsJSON();
    }

    //ÜBUNG: terminals? -> koordinaten, auth, 2 server?
}
