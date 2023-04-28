package ch.ilv.ebanking;

import ch.ilv.ebanking.address.Address;
import ch.ilv.ebanking.address.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DatabaseTest {
    private Address address = new Address();

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Order(1)
    void insertAddress() {
        address = new Address();
        address.setStreet("Wall Street");
        address = this.addressRepository.save(address);
        Assertions.assertNotNull(address.getId());
    }

    @Test
    @Order(2)
    void updateAddress() {
        address.setStreet("Wall-Street");
        address = this.addressRepository.save(address);
        Assertions.assertEquals("Wall-Street", address.getStreet());
    }

    @Test
    @Order(3)
    void getAddress() {
        Assertions.assertEquals(address.getStreet(), this.addressRepository.findById(address.getId()).get().getStreet());
    }

    @Test
    @Order(4)
    void deleteAddress() {
        this.addressRepository.deleteById(address.getId());
        Assertions.assertEquals(this.addressRepository.findById(address.getId()), Optional.empty());
    }
}