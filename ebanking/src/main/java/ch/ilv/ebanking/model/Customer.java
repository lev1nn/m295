package ch.ilv.ebanking.model;

import ch.ilv.ebanking.model.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100, nullable = false)
    @NotEmpty
    private String userName;
    @NotEmpty
    private String firstName;
    @Column(length = 100, nullable = false)
    @NotEmpty
    private String lastName;
    private int age;
    @OneToOne(optional = true)
    @JoinColumn(name = "address_id")
    private Address address;

    public Customer() {
    }
}

