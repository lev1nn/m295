package ch.ilv.ebanking.transaction;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name="bearerAuth")
@RestController
public class TransactionController {
    public final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
