package ch.ilv.ebanking.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private int streetNumber;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;

    public Address() {
    }

    public Address(String street, int streetNumber, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
    }
}
