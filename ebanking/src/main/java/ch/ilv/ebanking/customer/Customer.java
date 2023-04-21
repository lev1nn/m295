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
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;

    public Customer() {
    }
}

