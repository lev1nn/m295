package ch.ilv.ebanking.transaction;

import ch.ilv.ebanking.base.MessageResponse;
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

    public Transaction insertTransaction(Transaction Transaction) {
        return repository.save(Transaction);
    }

    public Transaction updateTransaction(Transaction Transaction, Long id) {
        return repository.findById(id)
                .map(TransactionOrig -> {
                    TransactionOrig.setPayingAccount(Transaction.getPayingAccount());
                    TransactionOrig.setReceivingAccount(Transaction.getReceivingAccount());
                    TransactionOrig.setAmount(Transaction.getAmount());
                    TransactionOrig.setTime(Transaction.getTime());
                    TransactionOrig.setDescription(Transaction.getDescription());
                    return repository.save(TransactionOrig);
                })
                .orElseGet(() -> repository.save(Transaction));
    }

    public MessageResponse deleteTransaction(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Transaction " + id + " deleted");
    }
}
