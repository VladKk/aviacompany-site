package aviacompany.repository;

import aviacompany.entity.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Репозиторій для диспетчера
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {
    //    Знайти всіх диспетчерів за іменем користувача та паролем
    List<Dispatch> findAllByIdAndUsernameAndPassword(long id, String username, String password);
}
