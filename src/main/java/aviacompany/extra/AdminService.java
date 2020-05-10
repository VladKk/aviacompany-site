package aviacompany.extra;

import aviacompany.entity.Admin;
import aviacompany.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Бізнес-логіка для адміністратора
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    //    Отримати логін для адміністратора за іменем користувача та паролем
    public boolean getLogin(long id, String username, String password) {
        List<Admin> admins = adminRepository.findAllByIdAndUsernameAndPassword(id, username, password);

        return !admins.isEmpty();
    }
}
