package ch.ilv.ebanking.service;

import ch.ilv.ebanking.base.MessageResponse;
import ch.ilv.ebanking.model.Customer;
import ch.ilv.ebanking.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getCustomers() {
        return repository.findByOrderByLastNameAscFirstNameAsc();
    }

    public Customer getCustomer(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Customer insertCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Customer updateCustomer(Customer customer, Long id) {
        return repository.findById(id)
                .map(customerOrig -> {
                    customerOrig.setUserName(customer.getUserName());
                    customerOrig.setFirstName(customer.getFirstName());
                    customerOrig.setLastName(customer.getLastName());
                    customerOrig.setAge(customer.getAge());
                    customerOrig.setAddress(customer.getAddress());
                    return repository.save(customerOrig);
                })
                .orElseGet(() -> repository.save(customer));
    }

    public MessageResponse deleteCustomer(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Customer " + id + " deleted");
    }

    public Customer getCustomerByJwt(Jwt jwt){
        String userName = jwt.getClaimAsString("preferred_username");
        return this.repository.getCustomerByUserName(userName)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "There doesn't exist a User with the Username " + userName )
                );
    }
}
