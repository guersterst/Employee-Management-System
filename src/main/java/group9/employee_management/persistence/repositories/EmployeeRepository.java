package group9.employee_management.persistence.repositories;

import group9.employee_management.persistence.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * A repository for user access.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("SELECT e.password FROM Employee e WHERE e.userName = :userName")
    String findPasswordByUserName(@Param("userName") String userName);

    @Query("SELECT e FROM Employee e WHERE e.userName = :userName")
    Employee getUserByUserName(@Param("userName") String userName);

    @Query("SELECT e.isFirstLogin FROM Employee e WHERE e.userName = :userName")
    boolean findIsFirstLoginByUserName(@Param("userName") String userName);

    @Query("SELECT CASE WHEN COUNT(e) = 0 THEN false ELSE true END FROM Employee e WHERE e.userName = :userName")
    boolean userExistsByUserName(@Param("userName") String userName);
}
