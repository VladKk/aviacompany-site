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

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "destination")
    private String destination;
    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private List<AddService> add_services;

    public double getFullPrice() {
        double fullPrice = price;

        for (AddService addService : add_services) {
            fullPrice += addService.getPrice();
        }

        return fullPrice;
    }
}
