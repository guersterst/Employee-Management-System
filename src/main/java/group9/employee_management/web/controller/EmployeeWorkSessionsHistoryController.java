package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
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
        model.addAttribute("workSessionsList", workSessionService.getSessions(principal.getName()));

        // Three work-sessions to be displayed on the work-session history. Mainly workSession1 will be used.
        model.addAttribute("workSession1", new WorkSessionDTO());
        model.addAttribute("workSession2", new WorkSessionDTO());
        model.addAttribute("workSession3", new WorkSessionDTO());
        model.addAttribute("status", new StatusDTO());
        return "history";
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
    //@ResponseBody
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
        return "history";
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
    //@ResponseBody
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
        return "history";
    }

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
            return "history";
        }

        if (session != null) {
            workSessionDTO = WorkSessionDTO.fromEntity(session);
        } else {
            status.setMessage("bad_request");
        }
        status.setMessage("valid");
        return "history";
    }

    /**
     * OUT OF ORDER AND NOT IN USER
     * Returns three sessions, beginning at the given index and descending from there. If there are less sessions
     * available, sessions of null will be filled in.
     *
     * @param principal Spring security principal.
     * @param workSession1 The first dto filled with the relevant info.
     * @param workSession2 The second dto filled with the relevant info.
     * @param workSession3 The third dto filled with the relevant info.
     * @return The view.
     */
    @Deprecated
    @GetMapping(
            value = "/session/three"
    )

    public String getThree(Principal principal,
                           @ModelAttribute("workSession1") WorkSessionDTO workSession1,
                           @ModelAttribute("workSession2") WorkSessionDTO workSession2,
                           @ModelAttribute("workSession3") WorkSessionDTO workSession3) {
        String userName = principal.getName();
        int index = workSession1.getId();

        List<WorkSession> threeFromIndex = workSessionService.getThreeFromIndex(userName, index);
        List<WorkSessionDTO> modelAttributes = List.of(workSession1, workSession2, workSession3);

        // Assign work-sessions to model-attributes
        for (int i = 0; i < 3; i++) {
            WorkSessionDTO modelAttribute = modelAttributes.get(i);
            if (threeFromIndex.get(i) != null) {
                modelAttribute = WorkSessionDTO.fromEntity(threeFromIndex.get(i));
            }
        }
        return "history";
    }

    /**
     * Downloads a JSON file containing all sessions.
     *
     * @param principal      Spring security principal.
     * @param status         The status dto.
     * @return The view.
     */
    @GetMapping(
            //TODO content negotiation
            value = "/downloadJSON"
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

    /*
    @GetMapping(
            //TODO content negotiation
            value = "/downloadCSV"
    )
    @ResponseBody
    public ResponseEntity<byte[]> getSessionsCSV(Principal principal, @ModelAttribute("status") StatusDTO status,
                                                 HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=sessions.csv");
        //workSessionService.workSessionsToCSV(response.getWriter(), principal.getName());

        byte[] customerCSVBytes;
        try {
            //customerCSVBytes = workSessionService.workSessionsToCSV(principal.getName()).getBytes();
        } catch (NoSessionsException | NoSuchUserException exception) {
            status.setMessage("bad_request");
            return null;
        }
        status.setMessage("valid");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sessions.json")
                .contentType(MediaType.APPLICATION_JSON);
                //.contentLength(customerCSVBytes.length)
                //.body(customerCSVBytes);
    }
     */
}
