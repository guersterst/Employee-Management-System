package group9.employee_management;

/**
 * Test model to check whether a post request from the login works.
 */
public class loginFormSubmission {
    private String userName;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
}