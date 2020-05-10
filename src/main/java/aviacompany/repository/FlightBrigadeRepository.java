package aviacompany.repository;

import aviacompany.entity.FlightBrigade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightBrigadeRepository extends JpaRepository<FlightBrigade, Long> {
    List<FlightBrigade> findAllByIdAndUsernameAndPassword(long id, String username, String password);
}
