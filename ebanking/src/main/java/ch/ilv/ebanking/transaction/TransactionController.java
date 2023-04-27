package ch.ilv.ebanking.transaction;

import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="bearerAuth")
@RestController
public class TransactionController {
    public final TransactionService TransactionService;

    public TransactionController(TransactionRepository TransactionRepository, TransactionService TransactionService) {
        this.TransactionService = TransactionService;
    }

    @GetMapping("api/Transaction")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Transaction>> all() {
        List<Transaction> result = TransactionService.getTransactions();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Transaction/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Transaction> one(@PathVariable Long id) {
        Transaction Transaction = TransactionService.getTransaction(id);
        return new ResponseEntity<>(Transaction, HttpStatus.OK);
    }

    @PostMapping("api/Transaction")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Transaction> newTransaction(@Valid @RequestBody Transaction Transaction) {
        Transaction savedTransaction = TransactionService.insertTransaction(Transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    }

    @PutMapping("api/Transaction/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Transaction> updateTransaction(@Valid @RequestBody Transaction Transaction, @PathVariable Long id) {
        Transaction savedTransaction = TransactionService.updateTransaction(Transaction, id);
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    }

    @DeleteMapping("api/Transaction/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteTransaction(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(TransactionService.deleteTransaction(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
