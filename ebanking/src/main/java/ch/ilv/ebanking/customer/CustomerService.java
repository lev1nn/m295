package ch.ilv.ebanking.customer;

import ch.ilv.ebanking.base.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getCustomers() {
        return repository.findByOrderByLastnameAscFirstnameAsc();
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
                    customerOrig.setFirstname(customer.getFirstname());
                    customerOrig.setLastname(customer.getLastname());
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
}
