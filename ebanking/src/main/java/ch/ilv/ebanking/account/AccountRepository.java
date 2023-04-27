package ch.ilv.ebanking.account;

import ch.ilv.ebanking.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOrderByAccountnameAsc();
}
