package group9.employee_management.web.controller;

import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/employee")
public class WorkSessionsController {


    private final WorkSessionService workSessionService;

    @Autowired
    public WorkSessionsController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Show the employees main page.
     *
     * @param model The model.
     * @return The employees main page.
     */
    @GetMapping(
            value = ""
    )
    public String get(Model model) {
        model.addAttribute("workSessionData", new WorkSessionDTO());
        return "employeeView";
    }

    /**
     * Starts the latest session for an employee.
     *
     * @param newSession A dto containing information about the desired new session.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     */
    @PostMapping(
            value = "/session-start"
    )
    @ResponseBody
    public HttpStatus startSession(@ModelAttribute("workSessionData") WorkSessionDTO newSession) {
        if (newSession.getUserName() != null && newSession.getTextStatus() != null) {
            workSessionService.startSession(newSession.getUserName(), newSession.getTextStatus(),
                    newSession.isAvailable(), newSession.isOnSite());
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session
     * @return
     */
    @PostMapping(
            value = "/session-end"
    )
    @ResponseBody
    public HttpStatus endSession(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        if (session.getUserName() != null) {
            workSessionService.stopSession(session.getUserName());
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    //PUT MESSAGETOLATEST

    //POST ENDWORKSESSION
    //PUT AVAILABILITY
    //GET AVAILABILITY
}
