package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees/worksessions")
public class WorkSessionsHistoryController {

    //AUTH

    private final WorkSessionService workSessionService;

    @Autowired
    public WorkSessionsHistoryController(WorkSessionService workSessionService) {
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
    public String get() {

        //TODO
        return "history";
    }

    /**
     * Get the latest work-session of a user. If that user has no sessions {@code HttpStatus.NOT_FOUND} will be
     * returned.
     * @param userName The user of whom we want to acquire the latest work-session.
     * @return The latest work-session as JSON.
     * @throws JsonProcessingException
     */
    @GetMapping(
            value = "/latest/{userName}"
    )
    @ResponseBody
    public String getLatest(@PathVariable(value = "userName") String userName) throws JsonProcessingException {
        return WorkSessionDTO.fromEntity(workSessionService.getLatest(userName)).toJSON();
    }

    /**
     * Returns the index of the latest work-session of an user. Indexing starts at 0, therefore -1 indicates that
     * there are no sessions for this user.
     * @param userName The user of whom we want to acquire the highest index.
     * @return The highest index.
     */
    @GetMapping(
            value = "/latest/{userName}/index"
    )
    @ResponseBody
    public int getIndex(@PathVariable(value = "userName") String userName) {
        return workSessionService.getIndex(userName);
    }

    /**
     * Returns three sessions, beginning at the given index and descending from there. If there are less sessions
     * available, less sessions will be returned. If there are no sessions the JSON array will be empty.
     *
     * @param userName The user of whom we want to acquire the sessions.
     * @param index The index at which the returned sessions begin.
     * @return At most three sessions in JSON.
     * @throws JsonProcessingException
     */
    @GetMapping(
            value = "/{userName}/{index}"
    )
    @ResponseBody
    public String getThree(@PathVariable(value = "userName") String userName,
                          @PathVariable(value = "index") int index) throws JsonProcessingException {
        return workSessionService.workSessionsToJSON(workSessionService.getThreeFromIndex(userName, index));
    }
}
