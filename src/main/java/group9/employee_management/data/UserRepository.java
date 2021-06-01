package group9.employee_management.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository of the user.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
