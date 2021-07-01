package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionListEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A controller to gain access to all users latest sessions.
 */
@Controller
@RequestMapping("/admin/employees")
public class EmployeeListView {

    private final WorkSessionService workSessionService;

    @Autowired
    public EmployeeListView(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Get the view and a DTO for all WorkSessionListEntries.
     *
     * @param model The Model.
     * @return The view.
     */
    @GetMapping(
            ""
    )
    public String get(Model model) {
        model.addAttribute("workSessionListEntries", workSessionService.getListEntries());
        return "adminView";
    }

    /**
     * OUT OF ORDER AND NOT IN USE.
     * <p>
     * Sets the dto of the list entry with the values of the latest session for a given user.
     *
     * @param userName                The users name.
     * @param workSessionListEntryDTO The DTO to be filled.
     * @param status                  The status.
     * @return The view.
     */
    @Deprecated
    @GetMapping(
            value = "/{userName}/session"
    )
    public String getLatestSessionAndUser(@PathVariable("userName") String userName,
                                          @ModelAttribute("workSessionListEntry") WorkSessionListEntryDTO workSessionListEntryDTO,
                                          @ModelAttribute("status") StatusDTO status) {
        try {
            workSessionListEntryDTO =
                    WorkSessionListEntryDTO.fromEntities(workSessionService.getUser(userName),
                            workSessionService.getLatest(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "adminView";
    }


    /**
     * Returns the user names of all users with currently ongoing work-sessions.
     * This is currently not working correctly due to issues with the foreign key and id.
     *
     * @return JSON format of {@code userName} array.
     */
    @GetMapping(
            value = "/working"
    )
    @ResponseBody
    public String getUsersWithRunningSessions() {

        // I think it is appropriate to return JSON here. Don't really want to introduce a DTO or something for this.
        return workSessionService.getUsersWithRunningSessionsAsJSON();
    }

    /**
     * Allows the admin to stop the latest session of an user.
     *
     * @param userName The users name.
     * @param status The status.
     * @return The view.
     */
    @PostMapping(
            value = "/{userName}/stop"
    )
    public String stopLatestSessionOfUser(@PathVariable("userName") String userName,
                                          @ModelAttribute("status") StatusDTO status) {
        try {
            workSessionService.stopSession(userName);
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "adminView";
    }
}
