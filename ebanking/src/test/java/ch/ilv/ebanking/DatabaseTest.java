package ch.ilv.ebanking;

import ch.ilv.ebanking.model.Address;
import ch.ilv.ebanking.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
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
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void testCrudMethods() {
        // Create
        Address address = new Address("Wall Street", 94, "New York", "USA");
        address = this.addressRepository.save(address);
        Assertions.assertNotNull(address.getId());

        // Update
        address.setStreet("Wall-Street");
        address = this.addressRepository.save(address);
        Assertions.assertEquals("Wall-Street", address.getStreet());

        // Read
        Assertions.assertEquals(address.getStreet(), this.addressRepository.findById(address.getId()).get().getStreet());

        // Delete
        this.addressRepository.deleteById(address.getId());
        Assertions.assertEquals(this.addressRepository.findById(address.getId()), Optional.empty());
    }
}