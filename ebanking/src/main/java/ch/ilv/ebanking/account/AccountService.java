package ch.ilv.ebanking.account;

import ch.ilv.ebanking.base.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAccounts() {
        return repository.findByOrderByAccountnameAsc();
    }

    public Account getAccount(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Account insertAccount(Account Account) {
        return repository.save(Account);
    }

    public Account updateAccount(Account Account, Long id) {
        return repository.findById(id)
                .map(AccountOrig -> {
                    AccountOrig.setAccountname(Account.getAccountname());
                    AccountOrig.setBalance(Account.getBalance());
                    AccountOrig.setCustomer(Account.getCustomer());
                    return repository.save(AccountOrig);
                })
                .orElseGet(() -> repository.save(Account));
    }

    public MessageResponse deleteAccount(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Account " + id + " deleted");
    }
}
