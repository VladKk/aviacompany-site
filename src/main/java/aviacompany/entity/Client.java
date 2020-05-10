package aviacompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    @Size(min = 4, max = 32, message = "Size: 4 to 32")
    private String first_name;
    @Column(name = "last_name")
    @Size(min = 4, max = 32, message = "Size: 4 to 32")
    private String last_name;
    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "password")
    @Size(min = 4, max = 32, message = "Size: 4 to 32")
    private String password;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket tickets;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}