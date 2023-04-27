package ch.ilv.ebanking.customer;

import ch.ilv.ebanking.address.Address;
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
    private String username;
    @NotEmpty
    private String firstname;
    @Column(length = 100, nullable = false)
    @NotEmpty
    private String lastname;
    private int age;
    @OneToOne(optional = true)
    @JoinColumn(name = "address_id")
    private Address address;

    public Customer() {
    }
}

