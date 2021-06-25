package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.WorkSessionDTO;
import group9.employee_management.web.dto.WorkSessionListEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeListView {

    //AUTH ADMIN

    private final WorkSessionService workSessionService;

    @GetMapping(
            ""
    )
    public String get(Model model) {
        model.addAttribute("workSessionListEntry", new WorkSessionListEntryDTO())
        return "adminView";
    }
    @Autowired
    public EmployeeListView(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @GetMapping(
            value = "/{userName}/session"
    )
    public String getLatestSessionAndUser(@PathVariable("userName") String userName) throws JsonProcessingException {
        WorkSessionListEntryDTO workSessionListEntryDTO =
                WorkSessionListEntryDTO.fromEntities(workSessionService.getUser(userName),
                        workSessionService.getLatest(userName));
        return workSessionListEntryDTO.toJSON();
    }

    //TODO
    /**
     * Returns the user names of all users with currently ongoing work-sessions.
     *
     * This is currently not working correctly due to issues with the foreign key and id.
     * @return
     */
    @GetMapping(
            value = "/working"
    )
    @ResponseBody
    public String getUsersWithRunningSessions() {
        return workSessionService.getUsersWithRunningSessionsAsJSON();
    }
}
