package group9.employee_management.web.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller to access information about employees and their current work-session.
 */
@RestController
@RequestMapping("/admin/employees")
public class EmployeeListController {

    //AUTH admin


    // TODO as json or set of UserDTOs?
    /**
     * Return all employees and their current work-session.
     */
    @GetMapping(
            value = ""
    )
    @ResponseBody
    public String getAllEmployees(){
        return "";
    }

    /*
     * GET User information
     * and latest worksession information (checkin, checkout, message)
     */
}
