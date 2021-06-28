package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller to access users work-sessions as an admin.
 */
@RestController
@RequestMapping("/admin/employees/work-sessions")
public class AdminWorkSessionsHistoryController {

    //AUTH

    // getSession(index)

    // getLatestThree()?

    private final WorkSessionService workSessionService;

    @Autowired
    public AdminWorkSessionsHistoryController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    /**
     * Access the html to display an users work-session history.
     *
     * @return The history.html
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
     * Get the latest work-session of a user. If that user has no sessions {@code HttpStatus.NOT_FOUND} will be
     * returned.
     *
     * @param userName The user of whom we want to acquire the latest work-session.
     * @return The latest work-session as JSON.
     * @throws JsonProcessingException
     */
    @GetMapping(
            value = "/latest/{userName}"
    )
    //@ResponseBody
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
     * @param userName The user of whom we want to acquire the highest index.
     * @return The highest index.
     */
    @GetMapping(
            value = "/latest/{userName}/index"
    )
    //@ResponseBody
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
     * Returns three sessions, beginning at the given index and descending from there. If there are less sessions
     * available, sessions of null will be filled in.
     *
     * @param userName The user of whom we want to acquire the sessions.
     * @param index    The index at which the returned sessions begin.
     * @return At most three sessions in JSON.
     */
    //TODO this mapping does not work right now. Please use above
    @GetMapping(
            //value = "/{userName}/{index}/three"
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
            if(modelAttribute != null) {
                System.out.println(modelAttribute.getTextStatus());
            }
        }

        status.setMessage("valid");
        return "history";
    }
}
