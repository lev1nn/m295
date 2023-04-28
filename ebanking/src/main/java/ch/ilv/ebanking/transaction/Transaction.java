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
    @JoinColumn(name = "paying_account_id")
    private Account payingAccount;
    @OneToOne(optional = false)
    @JoinColumn(name = "receiving_account_id")
    private Account receivingAccount;
    private double amount;
    private String time;
    private String description;
}
