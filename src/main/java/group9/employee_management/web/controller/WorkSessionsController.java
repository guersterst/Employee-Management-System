package group9.employee_management.web.controller;


import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import group9.employee_management.web.dto.WorkSessionListEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * A controller to gain access to your current session.
 */
@Controller
@RequestMapping(value = "/my-session")
public class WorkSessionsController {
    private final WorkSessionService workSessionService;

    @Autowired
    public WorkSessionsController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Show the employees main page. Introduces DTOS for the current WorkSession, latest history and an status.
     *
     * @param model The model.
     * @param principal. Description forthcoming.
     * @return The employees main page.
     */
    @GetMapping(
            value = ""
    )
    public String getString(Model model, Principal principal) {

        // This DTO is for the upper part of the page (my-session management).
        model.addAttribute("workSessionData", new WorkSessionDTO());

        List<WorkSessionDTO> sessions = workSessionService.getSessions(principal.getName());
        List<WorkSessionDTO> lastTwoSessions = List.of(sessions.get(sessions.size() - 1),
                sessions.get(sessions.size() - 2));

        // A DTO containing the last two sessions of an employee.
        model.addAttribute("workSessionListEntries", lastTwoSessions);

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
     * @param principal      Spring security principal.
     * @param workSessionDTO The work-session dto filled with relevant information.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/latest/index"
    )
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
     *
     * @param principal      Spring security principal.
     * @param workSessionDTO The work-session dto filled with relevant information.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/session"
    )
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
     * Get the latest work-session of a user.
     *
     * @param principal      Spring security principal.
     * @param workSessionDTO The work-session dto filled with relevant information.
     * @param status         The status dto.
     * @param model          The model.
     * @return
     */
    @GetMapping(
            value = "/latest"
    )
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
     * @param newSession The dto filled with relevant information.
     * @param status     The status dto.
     * @param principal  Spring security principal.
     * @return The view.
     */
    @PostMapping(
            value = "/beginning"
    )

    public String startSession(@ModelAttribute("workSessionData") WorkSessionDTO newSession,
                               @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            workSessionService.startSession(userName, newSession.getTextStatus(),
                    true, newSession.isOnSite());
        } catch (NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "redirect:/my-session/latest";
    }

    /**
     * Ends the latest session of a user.
     *
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @param model     The model.
     * @return The view.
     */
    @PostMapping(
            value = "/ending"
    )
    public String endSession(@ModelAttribute("workSessionData") WorkSessionDTO session,
                             @ModelAttribute("status") StatusDTO status, Principal principal, Model model) {
        String userName = principal.getName();

        try {
            workSessionService.stopSession(userName);
            session = WorkSessionDTO.fromEntity(workSessionService.getLatest(userName));
            model.addAttribute("workSessionData", session);
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "redirect:/my-session/latest";
    }

    /**
     * Puts a new message in the current running session of a user.
     *
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @return The view.
     */
    @PostMapping(
            value = "/message"
    )
    public String postMessage(@ModelAttribute("workSessionData") WorkSessionDTO session,
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
        return "redirect:/my-session/latest";
    }

    /**
     * Deletes a text message.
     *
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @param model     The model.
     * @return The view.
     */
    @PostMapping(
            value = "/message/delete"
    )
    public String deleteTextStatus(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                   @ModelAttribute("status") StatusDTO status, Principal principal, Model model) {
       String userName = principal.getName();

       try {
           workSessionService.deleteTextStatus(userName);
           session.setTextStatus("");
           model.addAttribute("workSessionData", session);
           System.out.println(session.getTextStatus());
       } catch (NoSessionsException | NoSuchUserException exception) {
           status.setMessage("bad_request");
       }
       status.setMessage("valid");
       return "redirect:/my-session/latest";
    }

    /**
     * Returns the message associated with the users latest session.
     *
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @return The view.
     */
    @GetMapping(
            value = "/message"
    )
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
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @return The view.
     */
    @PostMapping(
            value = "/availability"
    )
    public String putAvailability(@ModelAttribute("workSessionData") WorkSessionDTO session,
                                  @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            workSessionService.putAvailability(userName, session.isAvailable());
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "redirect:/my-session/latest";
    }

    /**
     * Returns the availability value of an users latest session.
     *
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @return The view.
     */
    @GetMapping(
            value = "/availability"
    )
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
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @return The view.
     */
    @PostMapping(
            value = "/onsite"
    )
    public String putOnSite(@ModelAttribute("workSessionData") WorkSessionDTO session,
                            @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            workSessionService.putOnSite(userName, session.isOnSite());
            if (!session.isOnSite()) {
                workSessionService.stopSession(userName);
            } else {

            }
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "redirect:/my-session/latest";
    }

    /**
     * Returns the onSite value of an users latest session.
     *
     * @param session   The work-session dto filled with the relevant information.
     * @param status    The status dto.
     * @param principal Spring security principal.
     * @return The view.
     */
    @GetMapping(
            value = "/onsite"
    )
    public String getOnSite(@ModelAttribute("workSessionData") WorkSessionDTO session,
                            @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        try {
            session.setOnSite(workSessionService.getOnSite(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "employeeView";
    }
}
