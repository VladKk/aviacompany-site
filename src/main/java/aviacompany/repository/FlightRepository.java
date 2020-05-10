package aviacompany.repository;

import aviacompany.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторій для польоту
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
