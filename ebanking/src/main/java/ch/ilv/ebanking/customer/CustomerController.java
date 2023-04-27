package ch.ilv.ebanking.customer;

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
public class CustomerController {
    public final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/Customer")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Customer>> all() {
        List<Customer> result = customerService.getCustomers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Customer/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Customer> one(@PathVariable Long id) {
        Customer Customer = customerService.getCustomer(id);
        return new ResponseEntity<>(Customer, HttpStatus.OK);
    }

    @PostMapping("api/Customer")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Customer> newCustomer(@Valid @RequestBody Customer Customer) {
        Customer savedCustomer = customerService.insertCustomer(Customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @PutMapping("api/Customer/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer Customer, @PathVariable Long id) {
        Customer savedCustomer = customerService.updateCustomer(Customer, id);
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
