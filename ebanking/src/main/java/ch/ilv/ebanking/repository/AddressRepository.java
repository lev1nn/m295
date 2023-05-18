package ch.ilv.ebanking.repository;

import ch.ilv.ebanking.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByOrderByCityAscStreetAsc();
}
