package group9.employee_management.data;

import org.springframework.data.repository.CrudRepository;

/**
 * A repository of the user.
 */
public interface UserRepository extends CrudRepository<User, String> {
}
