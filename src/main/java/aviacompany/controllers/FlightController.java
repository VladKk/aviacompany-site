package aviacompany.controllers;

import aviacompany.entity.Flight;
import aviacompany.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FlightController {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/flights")
    public String flights(Model model) {
        model.addAttribute("label", "Flights");
        model.addAttribute("flights", flightRepository.findAll());
        return "flight";
    }

    @GetMapping("/track_flight")
    public String track_flight(HttpServletRequest request, Model model) {
        model.addAttribute("label", "Track Flight");
        HttpSession session = request.getSession();

        Flight flight = (Flight) session.getAttribute("flight");
        model.addAttribute("flight", flight);

        return "track_flight";
    }

    @PostMapping("track_flight")
    public String trackFlight(@RequestParam("id") Flight flight,
                              HttpServletRequest request, Model model) {
        model.addAttribute("label", "Track Flight");
        HttpSession session = request.getSession();

        Flight flight1 = (Flight) session.getAttribute("flight");

        if (flight1 == null) {
            flight1 = new Flight();
            session.setAttribute("flight", flight1);
        }

        flight1 = flight;
        session.setAttribute("flight", flight1);

        return "track_flight";
    }
}
