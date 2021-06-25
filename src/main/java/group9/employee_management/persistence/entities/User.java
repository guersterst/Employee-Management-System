package group9.employee_management.persistence.entities;

import group9.employee_management.application.Roles;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Entity
public class User {

    @Id
    private String username;
    private String password;

    @OneToOne(optional = true)
    private Employee employee;

    @ElementCollection
    private List<Roles> roles;

    public User(String username, String password, Employee employee, Roles... roles) {
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.roles = new ArrayList<>(Arrays.asList(roles));
    }

    public User() {
    }

    public User(String username, String password, Employee employee, List<Roles> roles) {
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }


    public Optional<Employee> getEmployee() {
        return Optional.ofNullable(employee);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
