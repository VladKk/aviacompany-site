package aviacompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

// Клас сутності для польоту
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "departure")
    private String departure;
    @Column(name = "destination")
    private String destination;
    @Column(name = "flight_time")
    private String flight_time;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    @OneToOne
    @JoinColumn(name = "dispatch_id")
    private Dispatch dispatch;
    @OneToOne
    @JoinColumn(name = "flight_crew_id")
    private FlightBrigade flight_brigade;
}
