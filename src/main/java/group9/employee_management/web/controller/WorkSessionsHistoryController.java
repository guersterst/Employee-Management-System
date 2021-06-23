package group9.employee_management.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group9.employee_management.application.service.WorkSessionService;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees/worksessions")
public class WorkSessionsHistoryController {

    private final WorkSessionService workSessionService;

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
        //TODO exception
        return WorkSessionDTO.fromEntity(workSessionService.getLatest(userName)).toJSON();
    }

    @GetMapping(
            value = "/latest/index/{userName}"
    )
    @ResponseBody
    public int getIndex(@PathVariable(value = "userName") String userName) {
        return workSessionService.getIndex(userName);
    }

    @GetMapping(
            value = "/{userName}/{index}"
    )
    @ResponseBody
    public String getFive(@PathVariable(value = "userName") String userName,
                          @PathVariable(value = "index") int index) throws JsonProcessingException {
        StringBuilder jsonArrayResponse = new StringBuilder("{ \"workSessions\": [");

        for (WorkSession workSession :
                workSessionService.getFiveFromIndex(userName, index)) {
            jsonArrayResponse.append(WorkSessionDTO.fromEntity(workSession).toJSON());
            jsonArrayResponse.append(", ");
        }

        // Remove last comma.
        String result = jsonArrayResponse.substring(0,jsonArrayResponse.length() - 2);
        return result + "]}";
    }


    //TODO NEW PAGE AND CONTROLLER
    //PUT MESSAGETOLATEST

    //POST STARTWORKSESSION
    //POST ENDWORKSESSION
    //PUT AVAILABILITY
    //GET AVAILABILITY

    //AUTH none

    //identification of worksession via username and worksession date
    //-> meaning one worksession is one day.

    /*

    user:

    ws1 <- id of worksession
    ws2
    ws3

     */

    //CREATE worksession

    /*

    GET latest

    Option 2:
    GET 10 (index) -> GET10 0 (first 10), GET10 3 (sessions 30-40)
    return json

     */


    // LATEST

    //PUT message
    //GET message
    //DELETE message

    //PUT startTime
    //GET startime

    //PUT endTime
    //GET endtime

    //PUT availability
    //GET availability
}
