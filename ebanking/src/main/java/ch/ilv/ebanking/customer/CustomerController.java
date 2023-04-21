package ch.ilv.ebanking.customer;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.*;

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

    @RolesAllowed("admin")
    @PutMapping("/update-person")
    public String updatePerson(@RequestBody Customer customer) {
        //customerRepository.save(customer);
        return  customer.getFirstname() + " " + customer.getLastname() + " updated!";
    }

    @RolesAllowed("admin")
    @PutMapping("/delete-person")
    public String deletePerson(@RequestBody Customer customer) {
        //customerRepository.save(customer);
        return  customer.getFirstname() + " " + customer.getLastname() + " deleted!";
    }
}
