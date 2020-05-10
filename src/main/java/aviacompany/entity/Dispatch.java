package aviacompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

// Клас сутності для диспетчера
@Entity
@Table(name = "dispatch")
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "dispatch_flight_crew",
            joinColumns = @JoinColumn(name = "dispatch_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "flight_crew_id", referencedColumnName = "id")
    )
    private List<FlightBrigade> flight_brigades;
}
