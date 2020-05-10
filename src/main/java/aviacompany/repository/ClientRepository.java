package aviacompany.repository;

import aviacompany.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Репозиторій для клієнта
public interface ClientRepository extends JpaRepository<Client, Long> {
    //    Знайти всіх клієнтів за електронною поштою та паролем
    List<Client> findAllByEmailAndPassword(String email, String password);
}
