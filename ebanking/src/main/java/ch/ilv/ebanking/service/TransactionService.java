package ch.ilv.ebanking.service;

import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.model.Account;
import ch.ilv.ebanking.model.Transaction;
import ch.ilv.ebanking.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountService accountService;
    public TransactionService(TransactionRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public List<Transaction> getTransactions() {
        return repository.findByOrderByTimeAsc();
    }

    public Transaction getTransaction(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Transaction insertTransaction(Transaction transaction) {
        Account payingAccount = this.accountService.getAccount(transaction.getPayingAccount().getId());
        Account receivingAccount = this.accountService.getAccount(transaction.getReceivingAccount().getId());

        payingAccount.setBalance(payingAccount.getBalance() - transaction.getAmount());
        receivingAccount.setBalance(receivingAccount.getBalance() + transaction.getAmount());

        this.accountService.updateAccount(payingAccount, transaction.getPayingAccount().getId());
        this.accountService.updateAccount(receivingAccount, transaction.getReceivingAccount().getId());

        return repository.save(transaction);
    }

    public Transaction updateTransaction(Transaction transaction, Long id) {
        return repository.findById(id)
                .map(transactionOrig -> {
                    transactionOrig.setPayingAccount(transaction.getPayingAccount());
                    transactionOrig.setReceivingAccount(transaction.getReceivingAccount());
                    transactionOrig.setAmount(transaction.getAmount());
                    transactionOrig.setTime(transaction.getTime());
                    transactionOrig.setDescription(transaction.getDescription());
                    return repository.save(transactionOrig);
                })
                .orElseGet(() -> repository.save(transaction));
    }

    public MessageResponse deleteTransaction(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Transaction " + id + " deleted");
    }
}
