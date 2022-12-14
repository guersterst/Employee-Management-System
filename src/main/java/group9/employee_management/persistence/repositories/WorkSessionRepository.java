package group9.employee_management.persistence.repositories;

import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSessionRepository extends JpaRepository<WorkSession, Integer> {

    @Query("SELECT w "
            + "FROM Employee e, WorkSession w "
            + "WHERE w.id.employee = (SELECT e FROM Employee e WHERE e.userName = :userName)"
            + "AND w.id.index = :index")
    WorkSession getWorkSession(@Param("userName") String userName, @Param("index") int index);

    @Query("SELECT MAX(w.id.index)"
            + "FROM WorkSession w "
            + "WHERE w.id.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    Integer getIndex(@Param("userName") String userName);

    @Query("SELECT w.textStatus "
            + "FROM Employee e, WorkSession w "
            + "WHERE w.id.employee = (SELECT e FROM Employee e WHERE e.userName = :userName)"
            + "AND w.id.index = :index")
    String getTextStatus(@Param("userName") String userName, @Param("index") int index);

    @Query("SELECT w.available from"
            + " WorkSession w WHERE w.id.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    boolean getAvailability(@Param("userName") String userName);

    @Query("SELECT w.onSite from"
            + " WorkSession w WHERE w.id.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    boolean getOnSite(@Param("userName") String userName);

    @Query("SELECT DISTINCT w.id.employee FROM WorkSession w WHERE w.stopTime IS NULL")
    List<Employee> getEmployeesWithRunningSessions();

    @Query("SELECT DISTINCT w.id.employee FROM WorkSession w")
    List<Employee> getAllEmployeesWithSessions();

    @Query("SELECT u FROM Employee u WHERE u.userName = :userName")
    Employee getEmployeeByUserName(@Param("userName") String userName);
}
