package ch.ilv.ebanking.account;

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
public class AccountController {
    public final AccountService AccountService;

    public AccountController(AccountRepository AccountRepository, AccountService AccountService) {
        this.AccountService = AccountService;
    }

    @GetMapping("api/Account")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Account>> all() {
        List<Account> result = AccountService.getAccounts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Account/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Account> one(@PathVariable Long id) {
        Account Account = AccountService.getAccount(id);
        return new ResponseEntity<>(Account, HttpStatus.OK);
    }

    @PostMapping("api/Account")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Account> newAccount(@Valid @RequestBody Account Account) {
        Account savedAccount = AccountService.insertAccount(Account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @PutMapping("api/Account/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Account> updateAccount(@Valid @RequestBody Account Account, @PathVariable Long id) {
        Account savedAccount = AccountService.updateAccount(Account, id);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @DeleteMapping("api/Account/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(AccountService.deleteAccount(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
