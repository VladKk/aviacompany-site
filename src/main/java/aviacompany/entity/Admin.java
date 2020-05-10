package aviacompany.entity;

import lombok.*;

import javax.persistence.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "admin")
    private List<Client> clients;

    @ManyToMany
    @JoinTable(
            name = "admin_dispatch",
            joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dispatch_id", referencedColumnName = "id")
    )
    private List<Dispatch> dispatches;
    @ManyToMany
    @JoinTable(
            name = "admin_flight_crew",
            joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "flight_crew_id", referencedColumnName = "id")
    )
    private List<FlightBrigade> flight_brigades;
}