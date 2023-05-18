package ch.ilv.ebanking.service;

import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.model.Account;
import ch.ilv.ebanking.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAccounts() {
        return repository.findByOrderByAccountNameAsc();
    }

    public Account getAccount(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Account insertAccount(Account account) {
        return repository.save(account);
    }

    public Account updateAccount(Account account, Long id) {
        return repository.findById(id)
                .map(accountOrig -> {
                    accountOrig.setAccountName(account.getAccountName());
                    accountOrig.setBalance(account.getBalance());
                    accountOrig.setCustomer(account.getCustomer());
                    return repository.save(accountOrig);
                })
                .orElseGet(() -> repository.save(account));
    }

    public MessageResponse deleteAccount(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Account " + id + " deleted");
    }

    public List<Account> getAccountsOfCustomer(long customerId){
        return this.repository.findAccountByCustomerId(customerId);
    }
}
