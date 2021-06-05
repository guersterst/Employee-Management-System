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

    @Query("SELECT u.firstName FROM User u WHERE u.id = :id")
    String findFirstNameById(@Param("id") String id);

    @Query("SELECT u.lastName FROM User u WHERE u.id = :id")
    String findLastNameById(@Param("id") String id);

    @Query("SELECT u.password FROM User u WHERE u.id = :id")
    String findPasswordById(@Param("id") String id);

    @Query("SELECT u.isAdmin FROM User u WHERE u.id = :id")
    boolean findIsAdminById(@Param("id") String id);

    @Query("SELECT u.isFirstLogin FROM User u WHERE u.id = :id")
    boolean findIsFirstLoginById(@Param("id") String id);

    @Query("SELECT u.token FROM User u WHERE u.id = :id")
    String findTokenById(@Param("id") String id);
}
