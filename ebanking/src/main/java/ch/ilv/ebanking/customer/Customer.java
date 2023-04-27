package ch.ilv.ebanking.customer;

import ch.ilv.ebanking.account.Account;
import ch.ilv.ebanking.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    @NotEmpty
    private Long id;
    @Column(length = 100, nullable = false)
    @NotEmpty
    private String firstname;
    @Column(length = 100, nullable = false)
    @NotEmpty
    private String lastname;
    @Size(min = 14)
    @NotEmpty
    private int age;
    @OneToOne(optional = false)
    @JoinColumn(name = "id")
    private Address address;

    public Customer() {
    }
}

