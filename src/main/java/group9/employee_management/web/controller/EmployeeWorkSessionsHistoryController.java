package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/my-history")
public class EmployeeWorkSessionsHistoryController {
    //AUTH

    // getSession(index)

    // getLatestThree()?

    private final WorkSessionService workSessionService;

    @Autowired
    public EmployeeWorkSessionsHistoryController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Access the html to display an users work-session history.
     *
     * @return The history.html
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
     * Get the latest work-session of a user. If that user has no sessions {@code HttpStatus.NOT_FOUND} will be
     * returned.
     * It is saved in "workSession1" DTO.
     * @return The latest work-session as JSON.
     * @throws JsonProcessingException
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
     * The index is written into "workSession1" DTO
     * @param userName The user of whom we want to acquire the highest index.
     * @return The highest index.
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
     * Returns three sessions, beginning at the given index and descending from there.
     *
     * Fill "workSession1" DTO with the desired index.
     *
     * @param userName The user of whom we want to acquire the sessions.
     * @param index The index at which the returned sessions begin.
     * @return At most three sessions in JSON.
     */
    @GetMapping(
            value = "/session/three"
    )
    //@ResponseBody
    //TODO does not work right now
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
}
