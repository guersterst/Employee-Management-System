package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees/worksessions")
public class WorkSessionsHistoryController {

    private final WorkSessionService workSessionService;
    //TODO defensiveness what if no session

    //AUTH

    @Autowired
    public WorkSessionsHistoryController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @GetMapping(
            value = ""
    )
    public String get() {

        //TODO
        return "history";
    }

    @GetMapping(
            value = "/latest/{userName}"
    )
    @ResponseBody
    public String getLatest(@PathVariable(value = "userName") String userName) throws JsonProcessingException {
        return WorkSessionDTO.fromEntity(workSessionService.getLatest(userName)).toJSON();
    }

    /**
     * -1 indicates that there are no sessions for this user
     * @param userName
     * @return
     */
    @GetMapping(
            value = "/latest/{userName}/index"
    )
    @ResponseBody
    public int getIndex(@PathVariable(value = "userName") String userName) {
        return workSessionService.getIndex(userName);
    }

    @GetMapping(
            value = "/{userName}/{index}"
    )
    @ResponseBody
    public String getThree(@PathVariable(value = "userName") String userName,
                          @PathVariable(value = "index") int index) throws JsonProcessingException {
        return workSessionService.workSessionsToJSON(workSessionService.getThreeFromIndex(userName, index));
    }
}
