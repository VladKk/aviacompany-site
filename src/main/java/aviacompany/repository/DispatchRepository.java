package aviacompany.repository;

import aviacompany.entity.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispatchRepository extends JpaRepository<Dispatch, Long> {
    List<Dispatch> findAllByIdAndUsernameAndPassword(long id, String username, String password);
}
