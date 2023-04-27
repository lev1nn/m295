package ch.ilv.ebanking.account;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name="bearerAuth")
@RestController
public class AccountController {
    public final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
