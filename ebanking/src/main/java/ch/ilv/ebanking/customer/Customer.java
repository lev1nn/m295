package ch.ilv.ebanking.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String firstname;
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String lastname;

    public Customer() {
    }
}
