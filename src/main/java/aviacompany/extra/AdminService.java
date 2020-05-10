package aviacompany.extra;

import aviacompany.entity.Admin;
import aviacompany.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean getLogin(long id, String username, String password) {
        List<Admin> admins = adminRepository.findAllByIdAndUsernameAndPassword(id, username, password);

        return !admins.isEmpty();
    }
}
