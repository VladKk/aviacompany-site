package aviacompany.repository;

import aviacompany.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Репозиторій для адміністратора
public interface AdminRepository extends JpaRepository<Admin, Long> {
    //    Знайти всіх адміністраторів за іменем користувача та паролем
    List<Admin> findAllByIdAndUsernameAndPassword(long id, String username, String password);
}
