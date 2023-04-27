package ch.ilv.ebanking.transaction;

import ch.ilv.ebanking.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "id")
    private Account payingAccount;
    @OneToOne(optional = false)
    @JoinColumn(name = "id")
    private Account receivingAccount;
    @NotEmpty
    private double amount;
    @NotEmpty
    private String time;
    @NotEmpty
    private String description;
}
