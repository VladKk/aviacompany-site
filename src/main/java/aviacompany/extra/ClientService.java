package aviacompany.extra;

import aviacompany.entity.Client;
import aviacompany.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Бізнес-логіка для клієнта
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //    Отримати логін за електорнною поштою та паролем
    public boolean getLogin(String email, String password) {
        List<Client> clients = clientRepository.findAllByEmailAndPassword(email, password);
        return !clients.isEmpty();
    }
}
