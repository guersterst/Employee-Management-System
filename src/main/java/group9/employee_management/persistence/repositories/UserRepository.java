package group9.employee_management.persistence.repositories;

import group9.employee_management.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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

    @Query("SELECT u.isFirstLogin FROM User u WHERE u.id = :id")
    boolean findIsFirstLoginById(@Param("id") String id);

    @Query("SELECT CASE WHEN COUNT(u) = 0 THEN false ELSE true END FROM User u WHERE u.name like :name")
    boolean userExistsByName(@Param("name") String name);

    @Query("SELECT u.id FROM User u WHERE u.name LIKE :name")
    String findIdByName(@Param("name") String name);
}
