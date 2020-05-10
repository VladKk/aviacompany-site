package aviacompany.repository;

import aviacompany.entity.FlightBrigade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Репозиторій для льотної бригади
public interface FlightBrigadeRepository extends JpaRepository<FlightBrigade, Long> {
    //    Знайти всі льотні бригади за іменем користувача та паролем
    List<FlightBrigade> findAllByIdAndUsernameAndPassword(long id, String username, String password);
}
