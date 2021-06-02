package group9.employee_management.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * A repository of the user.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u.name FROM User u WHERE u.id = :id")
    String findNameById(@Param("id") String id);

    @Query("SELECT u.password FROM User u WHERE u.id = :id")
    String findPasswordById(@Param("id") String id);

    @Query("SELECT u.isAdmin FROM User u WHERE u.id = :id")
    boolean findIsAdminById(@Param("id") String id);
}
