package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.hibernate.event.spi.PreInsertEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/my-session")
public class WorkSessionsController {

    //TODO what if there is no session yet, solution like in getAvailability?
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

        // This DTO is for the upper part of the page (my-session management).
        model.addAttribute("workSessionData", new WorkSessionDTO());

        // This DTO is for the lower part of the page (my history of sessions)
        model.addAttribute("historyWorkSessionDTO", new WorkSessionDTO());

        // This DTO is used to indicate the success of an operation.
        model.addAttribute("status", new StatusDTO());
        return "employeeView";
    }

    /**
     * Returns the index of the latest work-session of an user. Indexing starts at 0, therefore -1 indicates that
     * there are no sessions for this user.
     * @param userName The user of whom we want to acquire the highest index.
     * @return The highest index.
     */
    @GetMapping(
            value = "/latest/index"
    )
    //@ResponseBody
    public String getIndex(Principal principal,
                           @ModelAttribute("historyWorkSessionDTO") WorkSessionDTO workSessionDTO,
                           @ModelAttribute("status") StatusDTO status) {
        String userName = principal.getName();

        if (workSessionService.getLatest(userName) != null) {
            workSessionDTO.setId(workSessionService.getIndex(userName));
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Fill the historyWorkSessionDTO with the index of the session that you want to acquire.
     */
    @GetMapping(
            value = "/session"
    )
    //@ResponseBody
    public String getSessionByIndex(Principal principal,
                                    @ModelAttribute("historyWorkSessionDTO") WorkSessionDTO workSessionDTO,
                                    @ModelAttribute("status") StatusDTO status) {
        String userName = principal.getName();

        if (workSessionService.getOneFromIndex(userName, workSessionDTO.getId()) != null) {
            workSessionDTO
                    = WorkSessionDTO.fromEntity(workSessionService.getOneFromIndex(principal.getName(), workSessionDTO.getId()));
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Get the latest work-session of a user. If that user has no sessions {@code HttpStatus.NOT_FOUND} will be
     * returned.
     * @return The latest work-session as JSON.
     */
    @GetMapping(
            value = "/latest"
    )
    //@ResponseBody
    public String getLatest(Principal principal,
                            @ModelAttribute("workSessionData") WorkSessionDTO workSessionDTO,
                            @ModelAttribute("status") StatusDTO status) throws JsonProcessingException {
        if (workSessionService.getLatest(principal.getName()) != null) {
            workSessionDTO = WorkSessionDTO.fromEntity(workSessionService.getLatest(principal.getName()));
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Starts the latest session for an employee.
     *
     * @param newSession A dto containing information about the desired new session. Requires {@code userName},
     *                   {@code textStatus}, {@code availability} and {@code onSite}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise. {@code HttpStatus
     * .NOT_FOUND} if that user does not exist or has no sessions.
     */
    @PostMapping(
            value = "/beginning"
    )
    //@ResponseBody
    public String startSession(@ModelAttribute("workSessionData") WorkSessionDTO newSession,
                               @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (newSession.getTextStatus() != null) {
            workSessionService.startSession(principal.getName(), newSession.getTextStatus(),
                    newSession.isAvailable(), newSession.isOnSite());
            status.setMessage("valid");
            //return HttpStatus.OK;
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "employeeView";
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     * {@code HttpStatus.NOT_FOUND} if that user does not exist or has no sessions.
     */
    @PutMapping(
            value = "/ending"
    )
    //@ResponseBody
    public String endSession(@ModelAttribute("workSessionData") WorkSessionDTO session,
                             @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            workSessionService.stopSession(principal.getName());
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Puts a new message in the current running session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires {@code userName}, {@code
     *                textStatus}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise. Returns {@code
     * HttpStatus.GONE} if this session has already ended. {@code HttpStatus.NOT_FOUND} if that user does not exist
     * or has no sessions.
     */
    @PostMapping(
            value = "/message"
    )
    //@ResponseBody
    public String putMessage(@ModelAttribute("workSessionData") WorkSessionDTO session,
                             @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (session.getTextStatus() == null || workSessionService.getUser(principal.getName()) == null) {

            // Check for necessary fields.
            //return HttpStatus.BAD_REQUEST;
            status.setMessage("bad_request");
        } else if (session.getStopTime() != null) {

            // Check if the session has ended.
            //return HttpStatus.GONE;
            status.setMessage("too_late");
        } else {

            // Put the message.
            workSessionService.putMessage(principal.getName(), session.getTextStatus());
            //return HttpStatus.OK;
            status.setMessage("valid");
        }
        return "employeeView";
    }

    @DeleteMapping(
            value = "/message"
    )
    public String deleteTextStatus(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            workSessionService.deleteTextStatus(principal.getName());
            session.setTextStatus("");
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Returns the message associated with the users latest session.
     *
     * @param userName The users identifying name.
     * @return The message.
     */
    @GetMapping(
            value = "/message"
    )
    //@ResponseBody
    public String getTextStatus(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            session.setTextStatus(workSessionService.getTextStatus(principal.getName()));
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     * {@code HttpStatus.NOT_FOUND} if that user does not exist or has no sessions.
     */
    @PutMapping(
            value = "/availability"
    )
    //@ResponseBody
    public String putAvailability(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                  @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            workSessionService.putAvailability(principal.getName(), session.isAvailable());
            //return HttpStatus.OK;
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "employeeView";
    }

    /**
     * Returns the availability value of an users latest session. Returns {@code HttpStatus.NOT_FOUND} if that user
     * does not  exist or has no sessions.
     *
     * @param userName The users identifying name.
     * @return The availability value.
     */
    @GetMapping(
            value = "/availability"
    )
    //@ResponseBody
    public String getAvailability(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                  @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            session.setAvailable(workSessionService.getAvailability(principal.getName()));
            status.setMessage("valid");
        } else if (workSessionService.getLatest(principal.getName()) == null) {

            // Indicate that this user does not have a session
            status.setMessage("too_early");
        } else {
            // Indicate that this user isnt an employee
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     * {@code HttpStatus.NOT_FOUND} if that user does not exist or has no sessions.
     */
    @PutMapping(
            value = "/onsite"
    )
    //@ResponseBody
    public String putOnSite(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            workSessionService.putOnSite(session.getUserName(), session.isOnSite());
            //return HttpStatus.OK;
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "employeeView";
    }

    /**
     * Returns the onSite value of an users latest session.
     * <p>
     * ({@code HttpStatus.NOT_FOUND} if that user does not exist or has no sessions.)
     *
     * @param userName The users identifying name.
     * @return The onsite value.
     */
    @GetMapping(
            value = "/onsite"
    )
    //@ResponseBody
    public String getOnSite(@ModelAttribute("workSessionData") WorkSessionDTO session,
                            @ModelAttribute("status") StatusDTO status, Principal principal) {
        if (workSessionService.getUser(principal.getName()) != null) {
            session.setOnSite(workSessionService.getOnSite(principal.getName()));
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }
}
