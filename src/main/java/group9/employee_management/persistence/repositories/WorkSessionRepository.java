package group9.employee_management.persistence.repositories;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSessionRepository extends JpaRepository<WorkSession, Integer> {

    @Query("SELECT w FROM User u, WorkSession w WHERE w.user = ("
            + "SELECT u FROM User u WHERE u.userName =: userName)")
    WorkSession getLatestWorkSession(@Param("userName") String userName);
}
