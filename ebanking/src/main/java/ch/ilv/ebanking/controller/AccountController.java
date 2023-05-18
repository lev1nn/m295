package ch.ilv.ebanking.controller;

import ch.ilv.ebanking.service.AccountService;
import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.model.Account;
import ch.ilv.ebanking.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="bearerAuth")
@RestController
@Validated
public class AccountController {
    public final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("api/Account")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Account>> all() {
        List<Account> result = accountService.getAccounts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Account/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Account> one(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("api/Account")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Account> newAccount(@Valid @RequestBody Account account) {
        Account savedAccount = accountService.insertAccount(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @PutMapping("api/Account/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Account> updateAccount(@Valid @RequestBody Account account, @PathVariable Long id) {
        Account savedAccount = accountService.updateAccount(account, id);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @DeleteMapping("api/Account/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(accountService.deleteAccount(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
