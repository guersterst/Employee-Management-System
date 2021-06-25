package group9.employee_management.persistence.repositories;


import group9.employee_management.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
