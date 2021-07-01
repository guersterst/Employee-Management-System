package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("workSessionsList", workSessionService.getSessions(userName));

        // Three work-sessions to be displayed on the work-session history. Mainly workSession1 will be used.
        model.addAttribute("workSession1", new WorkSessionDTO());
        model.addAttribute("workSession2", new WorkSessionDTO());
        model.addAttribute("workSession3", new WorkSessionDTO());
        model.addAttribute("status", new StatusDTO());
        return "history";
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
                            @ModelAttribute("status") StatusDTO status) {
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
     * @param userName       The users name.
     * @param workSessionDTO The dto filled with the relevant info.
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
        return "history";
    }

    /**
     * Returns the session at the desired index.
     *
     * @param userName       The users name.
     * @param index          The index of the desired session.
     * @param workSessionDTO The dto filled with the relevant info.
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
     * @param userName     The users name.
     * @param index        The beginning-index of the desired sessions.
     * @param workSession1 The first dto filled with the relevant info.
     * @param workSession2 The second dto filled with the relevant info.
     * @param workSession3 The third dto filled with the relevant info.
     * @param status       Status dto
     * @return The view.
     */
    @Deprecated
    @GetMapping(
            value = "denied"
    )
    @ResponseBody
    public String getThree(@PathVariable(value = "userName") String userName,
                           @PathVariable(value = "index") int index,
                           @ModelAttribute("workSession1") WorkSessionDTO workSession1,
                           @ModelAttribute("workSession2") WorkSessionDTO workSession2,
                           @ModelAttribute("workSession3") WorkSessionDTO workSession3,
                           @ModelAttribute("status") StatusDTO status) {
        List<WorkSession> threeFromIndex;
        try {
            threeFromIndex = workSessionService.getThreeFromIndex(userName, index);
        } catch (NoSuchUserException exception) {
            status.setMessage("bad_request");
            return "bal";
        }
        List<WorkSessionDTO> modelAttributes = List.of(workSession1, workSession2, workSession3);

        if (threeFromIndex != null) {

            // Assign work-sessions to model-attributes
            for (int i = 0; i < 3; i++) {
                if (threeFromIndex.get(i) != null) {
                    // this assignment seems not to work
                    WorkSessionDTO el = modelAttributes.get(i);
                    el = WorkSessionDTO.fromEntity(threeFromIndex.get(i));;
                }
            }
        } else {
            status.setMessage("bad_request");
            return "bal";
        }

        for (WorkSessionDTO modelAttribute : modelAttributes) {
            if (modelAttribute != null) {
                System.out.println(modelAttribute.getTextStatus());
            }
        }

        status.setMessage("valid");
        return "history";
    }

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
        return "history";
    }
}
