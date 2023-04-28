package ch.ilv.ebanking;

import ch.ilv.ebanking.address.Address;
import ch.ilv.ebanking.address.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DatabaseTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void insertAddress() {
        Address testAddress = this.addressRepository.save(new Address("Wallstreet", 134, "New York", "USA"));
        Assertions.assertNotNull(testAddress.getStreet());
    }
}