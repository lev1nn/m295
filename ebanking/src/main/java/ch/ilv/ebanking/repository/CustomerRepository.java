package ch.ilv.ebanking.repository;

import ch.ilv.ebanking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByOrderByLastNameAscFirstNameAsc();

    Optional<Customer> getCustomerByUserName(String username);
}
