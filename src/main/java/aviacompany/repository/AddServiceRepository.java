package aviacompany.repository;

import aviacompany.entity.AddService;
import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторій для додаткових послуг
public interface AddServiceRepository extends JpaRepository<AddService, Long> {
}
