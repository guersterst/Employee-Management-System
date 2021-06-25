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
            + "WHERE w.employee = (SELECT e FROM Employee e WHERE e.userName = :userName)"
            + "AND w.index = :index")
    WorkSession getWorkSession(@Param("userName") String userName, @Param("index") int index);

    @Query("SELECT MAX(w.index)"
            + "FROM WorkSession w "
            + "WHERE w.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    Integer getIndex(@Param("userName") String userName);

    //TODO get only latest worksession
    //like in first query
    @Query("SELECT w.textStatus from"
            + " WorkSession w WHERE w.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    String getTextStatus(@Param("userName") String userName);

    @Query("SELECT w.available from"
            + " WorkSession w WHERE w.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    String getAvailability(@Param("userName") String userName);

    @Query("SELECT w.onSite from"
            + " WorkSession w WHERE w.employee = (SELECT u FROM Employee u WHERE u.userName = :userName)")
    String getOnSite(@Param("userName") String userName);

    @Query("SELECT DISTINCT w.employee FROM WorkSession w WHERE w.stopTime IS NULL")
    List<Employee> getUsersWithRunningSessions();

    @Query("SELECT u FROM Employee u WHERE u.userName = :userName")
    Employee getUserByUserName(@Param("userName") String userName);
}
