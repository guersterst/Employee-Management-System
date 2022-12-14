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
 * A controller to access users work-sessions as an admin.
 */
@Controller
@RequestMapping("/admin/employees/work-sessions")
public class AdminWorkSessionsHistoryController {
    private final WorkSessionService workSessionService;

    @Autowired
    public AdminWorkSessionsHistoryController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Access the html and gain access to all latest work-sessions of all users.
     * Of particular interest are the offered model-attributes for thymeleaf usage.
     *
     * @param model    The model.
     * @param userName The users name.
     * @return The view.
     */
    @GetMapping(
            value = "/{userName}"
    )
    public String get(Model model, @PathVariable("userName") String userName) {

        // Possibly this is the only DTO and mapping you need.
        try {
            model.addAttribute("workSessionsList", workSessionService.getSessions(userName));
        } catch (NoSessionsException | NoSuchUserException ex) {
            model.addAttribute("workSessionsList", Collections.emptyList());
        }

        // Work-session to be displayed on the work-session history.
        model.addAttribute("workSession1", new WorkSessionDTO());

        model.addAttribute("status", new StatusDTO());
        model.addAttribute("selectedUser", userName);
        return "historyView";
    }

    /**
     * Get the latest work-session of a user.
     *
     * @param userName       The users name.
     * @param workSessionDTO The dto filled with the relevant info.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/latest/{userName}"
    )
    public String getLatest(@PathVariable(value = "userName") String userName,
                            @ModelAttribute("workSession1") WorkSessionDTO workSessionDTO,
                            @ModelAttribute("status") StatusDTO status, Model model) {
        try {
            workSessionDTO = WorkSessionDTO.fromEntity(workSessionService.getLatest(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }

        try {
            model.addAttribute("workSessionsList", workSessionService.getSessions(userName));
        } catch (NoSessionsException | NoSuchUserException ex) {
            model.addAttribute("workSessionsList", Collections.emptyList());
        }

        model.addAttribute("adminVisitsPage", true);
        model.addAttribute("selectedUser", userName);
        status.setMessage("valid");
        return "historyView";
    }

    /**
     * Returns the index of the latest work-session of an user. Indexing starts at 0, therefore -1 indicates that
     * there are no sessions for this user.
     *
     * @param userName       The users name.
     * @param workSessionDTO The dto that is getting filled with the relevant info.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/latest/{userName}/index"
    )
    public String getIndex(@PathVariable(value = "userName") String userName,
                           @ModelAttribute("workSession1") WorkSessionDTO workSessionDTO,
                           @ModelAttribute("status") StatusDTO status) {

        try {
            workSessionDTO.setId(workSessionService.getIndex(userName));
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "historyView";
    }

    /**
     * Returns the session at the desired index.
     *
     * @param userName       The users name.
     * @param index          The index of the desired session.
     * @param workSessionDTO The dto that is getting filled with the relevant info.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/{userName}/{index}"
    )
    //@ResponseBody
    public String getSessionByIndex(@PathVariable("userName") String userName,
                                    @PathVariable("index") int index,
                                    @ModelAttribute("historyWorkSessionDTO") WorkSessionDTO workSessionDTO,
                                    @ModelAttribute("status") StatusDTO status) {
        WorkSession session = null;

        try {
            session = workSessionService.getOneFromIndex(userName, index);
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
     * Deletes a specified session
     *
     * @param userName The sessions owner.
     * @param index The sessions index.
     * @param status Status dto.
     * @return The view.
     */
    @DeleteMapping(
            value = "/{userName}/{index}"
    )
    public String deleteSession(@PathVariable(value = "userName") String userName,
                                @PathVariable(value = "index") int index, @ModelAttribute("status") StatusDTO status) {
        try {
            workSessionService.deleteSession(userName, index);
        } catch (NoSessionsException exception) {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "historyView";
    }

    /**
     * Exact copy of the method in EmployeeWorkSessionHistoryController, only adapted for the admin to use
     * a path-variable.
     * Downloads a XML file containing all sessions.
     *
     * @param principal      Spring security principal.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/download/{userName}/xml",
            produces = {"application/xml"}
    )
    @ResponseBody
    public ResponseEntity<byte[]> getSessionsXML(Principal principal,
                                                 @ModelAttribute("status") StatusDTO status,
                                                 @PathVariable("userName") String userName) {

        byte[] customerXMLBytes;
        try {
            customerXMLBytes = workSessionService.workSessionsToXML(userName).getBytes();
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

    /**
     * Exact copy of the method in EmployeeWorkSessionHistoryController, only adapted for the admin to use
     * a path-variable.
     * Downloads a JSON file containing all sessions.
     *
     * @param principal      Spring security principal.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            value = "/download/{userName}/json",
            produces = { "application/json"}
    )
    @ResponseBody
    public ResponseEntity<byte[]> getSessionsJSON(Principal principal,
                                                  @ModelAttribute("status") StatusDTO status,
                                                  @PathVariable("userName") String userName) {
        byte[] customerJsonBytes;
        try {
            customerJsonBytes = workSessionService.workSessionsToJSON(userName).getBytes();
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
}
