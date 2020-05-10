package aviacompany.repository;

import aviacompany.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByEmailAndPassword(String email, String password);
}
