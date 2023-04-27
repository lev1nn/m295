package ch.ilv.ebanking.account;

import ch.ilv.ebanking.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100, unique = true, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String accountName;
    private long balance;
    @OneToOne(optional = false)
    @JoinColumn(name = "id")
    private Customer customer;

    public Account() {
    }
}

