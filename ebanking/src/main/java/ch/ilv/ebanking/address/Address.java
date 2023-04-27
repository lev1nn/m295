package ch.ilv.ebanking.address;

import ch.ilv.ebanking.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String street;
    @NotEmpty
    private int streetNumber;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;
}
