package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
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
     *
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

        try {
            workSessionDTO.setId(workSessionService.getIndex(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
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
        int index = workSessionDTO.getId();
        WorkSession session = null;

        try {
            session = workSessionService.getOneFromIndex(userName, index);
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
            return "employeeView";
        }

        if (session != null) {
            workSessionDTO = WorkSessionDTO.fromEntity(session);
        } else {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }

    /**
     * Get the latest work-session of a user. If that user has no sessions {@code HttpStatus.NOT_FOUND} will be
     * returned.
     *
     * @return The latest work-session as JSON.
     */
    @GetMapping(
            value = "/latest"
    )
    //@ResponseBody
    public String getLatest(Principal principal,
                            @ModelAttribute("workSessionData") WorkSessionDTO workSessionDTO,
                            @ModelAttribute("status") StatusDTO status, Model model) {
        String userName = principal.getName();
        try {
            workSessionDTO = WorkSessionDTO.fromEntity(workSessionService.getLatest(userName));
            model.addAttribute("workSessionData", workSessionDTO);
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }

    /**
     * Starts the latest session for an employee.
     *
     * @param newSession A dto containing information about the desired new session. Requires
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
        String userName = principal.getName();

        try {
            workSessionService.startSession(userName, newSession.getTextStatus(),
                    newSession.isAvailable(), newSession.isOnSite());
        } catch (NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     * {@code HttpStatus.NOT_FOUND} if that user does not exist or has no sessions.
     */
    @PostMapping(
            value = "/ending"
    )
    //@ResponseBody
    public String endSession(@ModelAttribute("workSessionData") WorkSessionDTO session,
                             @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            workSessionService.stopSession(userName);
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
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
        String userName = principal.getName();

        if (session.getTextStatus() != null && session.getStopTime() == null) {
            try {
                workSessionService.putMessage(userName, session.getTextStatus());
            } catch (NoSessionsException | NoSuchUserException exception) {
                status.setMessage("bad_request");
            }
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "employeeView";
    }

    @DeleteMapping(
            value = "/message"
    )
    public String deleteTextStatus(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                   @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            workSessionService.deleteTextStatus(userName);
            session.setTextStatus("");
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
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
        String userName = principal.getName();

        try {
            session.setTextStatus(workSessionService.getTextStatus(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session A dto containing information about the desired new session. Requires only a {@code userName}.
     * @return {@code HttpStatus.OK} if successful, {@code HttpStatus.BAD_REQUEST} otherwise.
     * {@code HttpStatus.NOT_FOUND} if that user does not exist or has no sessions.
     */
    @PostMapping(
            value = "/availability"
    )
    //@ResponseBody
    public String putAvailability(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                  @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            workSessionService.putAvailability(userName, session.isAvailable());
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
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
        String userName = principal.getName();

        try {
            session.setAvailable(workSessionService.getAvailability(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }

    /**
     * Ends the latest session of a user if the onSite value is changed to false. And puts a new onSite value.
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
        String userName = principal.getName();

        try {
            workSessionService.putOnSite(userName, session.isOnSite());

            if (!session.isOnSite()) {
                workSessionService.stopSession(userName);
            }
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }

    /**
     * Returns the onSite value of an users latest session. A
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
        String userName = principal.getName();

        try {
            session.setOnSite(workSessionService.getOnSite(principal.getName()));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }
}
