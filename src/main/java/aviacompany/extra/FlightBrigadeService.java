package aviacompany.extra;

import aviacompany.entity.FlightBrigade;
import aviacompany.repository.FlightBrigadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Бізнес-логіка для льотної бригади
@Service
public class FlightBrigadeService {
    private final FlightBrigadeRepository flightBrigadeRepository;

    @Autowired
    public FlightBrigadeService(FlightBrigadeRepository flightBrigadeRepository) {
        this.flightBrigadeRepository = flightBrigadeRepository;
    }

    //    Отримати логін для льотної бригади за іменем користувача та паролем
    public boolean getLogin(long id, String username, String password) {
        List<FlightBrigade> flightBrigades = flightBrigadeRepository.findAllByIdAndUsernameAndPassword(id, username, password);
        return !flightBrigades.isEmpty();
    }
}
