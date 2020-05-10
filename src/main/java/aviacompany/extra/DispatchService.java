package aviacompany.extra;


import aviacompany.entity.Dispatch;
import aviacompany.repository.DispatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchService {
    private final DispatchRepository dispatchRepository;

    @Autowired
    public DispatchService(DispatchRepository dispatchRepository) {
        this.dispatchRepository = dispatchRepository;
    }

    public boolean getLogin(long id, String username, String password) {
        List<Dispatch> dispatches = dispatchRepository.findAllByIdAndUsernameAndPassword(id, username, password);

        return !dispatches.isEmpty();
    }
}
