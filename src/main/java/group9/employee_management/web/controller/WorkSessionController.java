package group9.employee_management.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/worksessions")
public class WorkSessionController {

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
