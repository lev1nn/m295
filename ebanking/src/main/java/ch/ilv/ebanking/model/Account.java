package ch.ilv.ebanking.model;

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
    private double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account() {
    }
}

