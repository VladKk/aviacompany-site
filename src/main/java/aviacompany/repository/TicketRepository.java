package aviacompany.repository;

import aviacompany.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторій для квитка
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
