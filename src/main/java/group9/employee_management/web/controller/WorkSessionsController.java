package group9.employee_management.web.controller;

import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/employee/session")
public class WorkSessionsController {

    //AUTH user

    //TODO defensiveness what if no session

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
     * @param newSession A dto containing information about the desired new session. Requires {@code userName},
     * {@code textStatus}, {@code availability} and {@code onSite}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     */
    @PostMapping(
            value = "/beginning"
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
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     */
    @PostMapping(
            value = "/ending"
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

    /**
     * Puts a new message in the current running session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires {@code userName}, {@code
     * textStatus}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise. Returns {@code
     * HttpStatus.GONE} if this session has already ended.
     */
    @PostMapping(
            value = "/message"
    )
    @ResponseBody
    public HttpStatus putMessage(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        if (session.getTextStatus() == null || session.getUserName() == null) {

            // Check for necessary fields.
            return HttpStatus.BAD_REQUEST;
        } else if (session.getStopTime() != null) {

            // Check if the session has not ended yet.
            return HttpStatus.GONE;
        } else {

            // Put the message.
            workSessionService.putMessage(session.getUserName(), session.getTextStatus());
            return HttpStatus.OK;
        }
    }

    //TODO javadoc
    //TODO defensiveness
    @GetMapping(
            value = "/message"
    )
    @ResponseBody
    public String getTextStatus(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        return workSessionService.getTextStatus(session.getUserName());
    }

    //TODO javadoc
    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     */
    @PostMapping(
            value = "/availability"
    )
    @ResponseBody
    public HttpStatus putAvailability(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        if (session.getUserName() != null) {
            workSessionService.putAvailability(session.getUserName(), session.isAvailable());
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    //TODO userName as pathvariable because its a get
    //TODO javadoc
    //TODO defensiveness
    @GetMapping(
            value = "/availability"
    )
    @ResponseBody
    public String getAvailability(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        return workSessionService.getAvailability(session.getUserName());
    }

    //TODO javadoc
    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     */
    @PostMapping(
            value = "/onsite"
    )
    @ResponseBody
    public HttpStatus putOnSite(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        if (session.getUserName() != null) {
            workSessionService.putOnSite(session.getUserName(), session.isOnSite());
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    //TODO javadoc
    //TODO defensiveness
    @GetMapping(
            value = "/onsite"
    )
    @ResponseBody
    public String getOnSite(@ModelAttribute("workSessionData") WorkSessionDTO session) {
        return workSessionService.getOnSite(session.getUserName());
    }
}
