package ch.ilv.ebanking.controller;

import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.service.CustomerService;
import ch.ilv.ebanking.model.Customer;
import ch.ilv.ebanking.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="bearerAuth")
@RestController
@Validated
public class CustomerController {
    public final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/Customer")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Customer>> all() {
        Jwt user = (Jwt)SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String preferredUsername = user.getClaim("preferred_username");

        List<Customer> result = customerService.getCustomers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Customer/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Customer> one(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("api/Customer")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Customer> newCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.insertCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @PutMapping("api/Customer/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, @PathVariable Long id) {
        Customer savedCustomer = customerService.updateCustomer(customer, id);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("api/Customer/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.deleteCustomer(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
