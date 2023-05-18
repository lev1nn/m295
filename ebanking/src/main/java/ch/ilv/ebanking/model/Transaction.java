package ch.ilv.ebanking.model;

import ch.ilv.ebanking.model.Account;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "paying_account_id")
    private Account payingAccount;
    @ManyToOne(optional = false)
    @JoinColumn(name = "receiving_account_id")
    private Account receivingAccount;
    private double amount;
    private String time;
    private String description;
}
