User Manual

for the employee management web application of group 9

The application is a standard spring boot java project. It runs on an embedded tomcat server, with an h2 in memory database. It can be started up by running the main class within EmployeeManagementApplication.java. As of now it is running locally (localhost) on port 8443 employing HTTPS. Therefore, it can be accessed using f.e. <https://localhost:8443/login>. We are asking for the trust-worthiness warning about this site to be ignored, it stems from a free and self-generated keytool certificate. We also ask for the permission alert about accessing the client’s location to be accepted. Out of time constraints, no terminal server could be implemented. As a result, we use the client’s geolocation to simulate some of its behaviours.

Valid login date can be found in DataInit.java. Two examples are (username: student, password: student, Role: User) or (username: admin, password: admin, Role: Admin).

After logging in as a user, you will see your latest work-session displayed. You can change and delete its attributes here. A session is stopped by unselecting ‘OnSite’ and started by reselecting it. You can also check out and modify your profile or view and download your work-session history.

After logging in as an admin you will see all users and their latest sessions. Check out and modify/delete their profiles from there or check out their individual work-session histories! Also, as an admin you have the possibility of creating new users.

We wish you productive work with our application!

In case any questions arise, please feel free to contact us. 


Group 9, consisting of

Stefanie Gürster

Elias Goller

and

Johannes Garstenauer





Who did what?

*Johannes Garstenauer:*

-Security configuration specifically:

- Authentication success handler
- User details service and encryption
- Web security configuration (authentication and roles, request authentication handling) with help of Elias Goller (please elaborate what you did in your part 😊)
- HTTPS setup

-All implementation and configuration concerning the persistence layer (database setup/init, repositories, entities, …)

-All implementation and configuration concerning the service layer (services, dtos, …)

-Controllers:

- AdminWorkSessionsHistoryController
- EmployeeWorkSessionsHistoryController
- EmployeeListViewController
- WorkSessionsController

According to Stefanie Gürster’s technical requirements and therefore in close collaboration.

Also:

-Controllers:

- UserCreationController

According to Elias Goller’s technical requirements and therefore in close collaboration.

-Controllers:

- AdminUserAccountManipulationController
- UserAccountManipulationController

For Elias Goller’s technical requirements. These were later largely revamped and improved by him.

In conclusion: Johannes Garstenauer’s work centred on everything back-end and spring boot.


