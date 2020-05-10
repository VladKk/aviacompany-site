package aviacompany.repository;

import aviacompany.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторій для користувача
public interface UserRepository extends JpaRepository<User, Long> {
    //    Знайти користувача за логіном
    User findByLogin(String login);
}
