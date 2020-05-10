package aviacompany.repository;

import aviacompany.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findAllByIdAndUsernameAndPassword(long id, String username, String password);
}
