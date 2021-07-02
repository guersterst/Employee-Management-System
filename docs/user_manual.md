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
- Web security configuration (authentication and roles, request authentication handling) with help of Elias Goller (fixed issues with using a custom login page and logout)
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

According to Elias Goller’s technical requirements and therefore in close collaboration.

-Controllers:

- UserCreationController
- UserAccountManipulationController

For Elias Goller’s technical requirements. These were later largely revamped and improved by him.

In conclusion: Johannes Garstenauer’s work centred on everything back-end and spring boot.


*Stefanie Gürster*

-Scenario

Frontend:
-employeeView the main page of the empolyee user.
-historyView shows all sessions that are stored or only view sessions if you use the filter.
-menubar fragment for every page.
-footer
-adminView displays all users and thier current view.

Backend:

In collaboration with Johannes Garstenauer: 
Improving the controller in order to fix bugs and being able to put front and backend together.

In conclusion:
My work was focused on the frontend but in order to put front and back together I hepled to fix the bugs.


*Elias Goller:*
- Wrote persona

Frontend:
- Created index.html for the login
- Created automaticLogout.js, which is used for logging out the user after two minutes of inactivity
- Created passwordMeterScript.js, which is used to check how strong a user's password is by using RegEx-patterns.
  A progress-bar is used to display the password's strength, a list displays the missing criteria for a secure     password.
- Created the userAccountPage, which displays a user's profile and also allows editing said profile. The view 
  can be used by user to see their profile or by admins to see any user's profile. The admin has additional abilities
  as they can delete the account, change the user's role/position and whether they are an admin or not. The admin also
  uses this view to get access to the user's history.
- Created the timerFragment to display a timer in the navbar when an automatic logout is imminent. In the last five
  seconds, the timer counts down from 5 to 0 and thus indicates to the user that they need to show acitivity to remain
  logged in.
- Created LoginForFirstTime, which is used to make the user change their password on their initial login
- Created the footer
- Created the adminCreateUserAccount view, which allows admins to create new users. This includes automatically
  generating a username. Moreover, on the right-side (or bottom, if the display is too small), the admin can create
  new roles/positions. Those can then be selected in the dropdown-menu on the left side for creating a user.
  These roles are stored via cookies. As the role/position is an attribute of the user, the admin would have to write
  down the position each time for a new user, which could lead to possible redundancies like different names for the
  same role. To combat this, this approach was chosen.

Backend:
- Additions to AccountService
- Implementing custom login page
- Controllers:
    - AdminUserAccountManipulationController
    - ErrorPageController
    - LoginController
    - LoginForFirstTimeController
    - Parts of UserAccountManipulationController

Overall, most of my work was focused on the frontend with some additional work in the backend.

More information can be found in the following issues:
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/26
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/25
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/24
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/23
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/22
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/21
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/20
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/19
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/18
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/16
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/13
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/8
https://git.fim.uni-passau.de/padas/21ss-web.data.eng.projects/g09/g9-21ss-web.data.eng/-/issues/9
