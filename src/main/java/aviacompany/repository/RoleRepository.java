package aviacompany.repository;

import aviacompany.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторій для ролі користувача
public interface RoleRepository extends JpaRepository<Role, Long> {
}
