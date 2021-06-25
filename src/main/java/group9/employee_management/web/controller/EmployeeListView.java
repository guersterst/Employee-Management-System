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
        model.addAttribute("workSessionListEntry", new WorkSessionListEntryDTO());
        return "adminView";
    }

    @Autowired
    public EmployeeListView(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    // Here the PathVariable is appropriate as it is not about an user getting information about himself.
    @GetMapping(
            value = "/{userName}/session"
    )
    public String getLatestSessionAndUser(@PathVariable("userName") String userName,
                                          @ModelAttribute("workSessionListEntry") WorkSessionListEntryDTO workSessionListEntryDTO) {
        workSessionListEntryDTO =
                WorkSessionListEntryDTO.fromEntities(workSessionService.getUser(userName),
                        workSessionService.getLatest(userName));
        return "adminView";
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
        //return workSessionService.getEmployeesWithRunningSessions();

        // I think it is appropriate to return JSON here. Don't really want to introduce a DTO or something for this.
        return workSessionService.getUsersWithRunningSessionsAsJSON();
    }
}
