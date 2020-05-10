package aviacompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import aviacompany.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {}
