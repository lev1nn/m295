package ch.ilv.ebanking.service;

import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.model.Transaction;
import ch.ilv.ebanking.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getTransactions() {
        return repository.findByOrderByTimeAsc();
    }

    public Transaction getTransaction(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Transaction insertTransaction(Transaction transaction) {
        transaction.getPayingAccount().setBalance((long) (transaction.getPayingAccount().getBalance() - transaction.getAmount()));
        transaction.getReceivingAccount().setBalance((long) (transaction.getReceivingAccount().getBalance() + transaction.getAmount()));
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
