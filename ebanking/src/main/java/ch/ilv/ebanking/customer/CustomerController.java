package ch.ilv.ebanking.customer;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name="bearerAuth")
@RestController
public class CustomerController {
    public final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RolesAllowed("admin")
    @PostMapping("/save-person")
    public String savePerson(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return  customer.getFirstname() + " " + customer.getLastname() + " saved!";
    }
}
