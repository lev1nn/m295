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
    public final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("api/Transaction")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Transaction>> all() {
        List<Transaction> result = transactionService.getTransactions();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Transaction/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Transaction> one(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransaction(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("api/Transaction")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Transaction> newTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.insertTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    }

    @PutMapping("api/Transaction/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Transaction> updateTransaction(@Valid @RequestBody Transaction transaction, @PathVariable Long id) {
        Transaction savedTransaction = transactionService.updateTransaction(transaction, id);
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    }

    @DeleteMapping("api/Transaction/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteTransaction(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(transactionService.deleteTransaction(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
