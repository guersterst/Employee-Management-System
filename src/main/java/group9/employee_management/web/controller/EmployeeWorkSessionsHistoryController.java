package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * A controller for an employee to get access to his own work-session history.
 */
@Controller
@RequestMapping("/my-history")
public class EmployeeWorkSessionsHistoryController {
    private final WorkSessionService workSessionService;

    @Autowired
    public EmployeeWorkSessionsHistoryController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Access the html and gain access to all latest work-sessions of all users.
     * Of particular interest are the offered model-attributes for thymeleaf usage.
     *
     * @param model     The model.
     * @param principal Spring security principal.
     * @return The view.
     */
    @GetMapping(
            value = ""
    )
    public String get(Model model, Principal principal) {

        // Possibly this is the only DTO and mapping you need.
        try {
            model.addAttribute("workSessionsList", workSessionService.getSessions(principal.getName()));
        } catch (NoSessionsException | NoSuchUserException ex) {
            model.addAttribute("workSessionsList", Collections.emptyList());
        }

        // Work-session to be displayed on the work-session history.
        model.addAttribute("workSession1", new WorkSessionDTO());
        model.addAttribute("status", new StatusDTO());
        model.addAttribute("adminVisitsPage", false);
        return "historyView";
    }

    /**
     * Get the users latest session.
     *
     * @param principal      Spring security principal.
     * @param workSessionDTO The dto filled with the relevant info.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/latest"
    )
    public String getLatest(Principal principal,
                            @ModelAttribute("workSession1") WorkSessionDTO workSessionDTO,
                            @ModelAttribute("status") StatusDTO status) {
        String userName = principal.getName();

        try {
            workSessionDTO = WorkSessionDTO.fromEntity(workSessionService.getLatest(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "redirect:/my-session/latest";
    }

    /**
     * Returns the index of the latest work-session of an user. Indexing starts at 0, therefore -1 indicates that
     * there are no sessions for this user.
     *
     * @param principal      Spring security principal.
     * @param workSessionDTO The dto filled with the relevant info.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/latest/index"
    )
    public String getIndex(Principal principal,
                           @ModelAttribute("workSession1") WorkSessionDTO workSessionDTO,
                           @ModelAttribute("status") StatusDTO status) {
        String userName = principal.getName();
        try {
            workSessionDTO.setId(workSessionService.getIndex(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "redirect:/my-session/latest";
    }


    @DeleteMapping(
            value = ""
    )

    /**
     * Returns the session at the desired index.
     *
     * @param principal      Spring security principal.
     * @param workSessionDTO The dto filled with the relevant info. (index)
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

        WorkSession session = null;

        try {
            session = workSessionService.getOneFromIndex(userName, workSessionDTO.getId());
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
            return "historyView";
        }

        if (session != null) {
            workSessionDTO = WorkSessionDTO.fromEntity(session);
        } else {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "historyView";
    }

    /**
     * Downloads a JSON file containing all sessions.
     *
     * @param principal      Spring security principal.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/download/json",
            produces = { "application/json"}
    )
    @ResponseBody
    public ResponseEntity<byte[]> getSessionsJSON(Principal principal,
                                                  @ModelAttribute("status") StatusDTO status) {
        byte[] customerJsonBytes;
        try {
            customerJsonBytes = workSessionService.workSessionsToJSON(principal.getName()).getBytes();
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
            return null;
        }
        status.setMessage("valid");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sessions.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(customerJsonBytes.length)
                .body(customerJsonBytes);
    }

    /**
     * Downloads a XML file containing all sessions.
     *
     * @param principal      Spring security principal.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/download/xml",
            produces = {"application/xml"}
    )
    @ResponseBody
    public ResponseEntity<byte[]> getSessionsXML(Principal principal,
                                                      @ModelAttribute("status") StatusDTO status) {
        byte[] customerXMLBytes;
        try {
            customerXMLBytes = workSessionService.workSessionsToXML(principal.getName()).getBytes();
        } catch (NoSessionsException | NoSuchUserException | IOException exception) {
            status.setMessage("bad_request");
            return null;
        }
        status.setMessage("valid");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sessions.xml")
                .contentType(MediaType.APPLICATION_XML)
                .contentLength(customerXMLBytes.length)
                .body(customerXMLBytes);
    }
}
