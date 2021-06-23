package group9.employee_management.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees/worksessions")
public class WorkSessionsHistoryController {

    @GetMapping(
            value = ""
    )
    @ResponseBody
    public String get(){

        //TODO
        return "history.html";
    }

    //GET LATEST
    //GET INDEX
    //GET 5 (INDEX)


    @GetMapping(
            value = "/latest/{userName}"
    )
    @ResponseBody
    public String getLatest(@PathVariable(value = "userName") String userName) {

    }

    @GetMapping(
            value = "/latest/index/{userName}"
    )
    @ResponseBody
    public String getIndex(@PathVariable(value = "userName") String userName) {

    }

    @GetMapping(
            value = "/{userName}/{index}"
    )
    @ResponseBody
    public String getFive(@PathVariable(value = "userName") String userName,
                          @PathVariable(value = "index") int index) {

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
