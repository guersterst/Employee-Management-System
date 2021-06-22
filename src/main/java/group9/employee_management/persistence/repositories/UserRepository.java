package group9.employee_management.persistence.repositories;

import group9.employee_management.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * A repository for user access.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u.password FROM User u WHERE u.userName = :userName")
    String findPasswordByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User getUserByUserName(@Param("userName") String userName);

    @Query("SELECT u.isFirstLogin FROM User u WHERE u.userName = :userName")
    boolean findIsFirstLoginByUserName(@Param("userName") String userName);

    @Query("SELECT CASE WHEN COUNT(u) = 0 THEN false ELSE true END FROM User u WHERE u.userName = :userName")
    boolean userExistsByUserName(@Param("userName") String userName);
}
