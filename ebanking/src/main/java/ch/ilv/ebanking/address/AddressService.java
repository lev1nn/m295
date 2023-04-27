package ch.ilv.ebanking.address;

import ch.ilv.ebanking.base.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public List<Address> getAddresses() {
        return repository.findByOrderByCityAscStreetAsc();
    }

    public Address getAddress(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Address insertAddress(Address Address) {
        return repository.save(Address);
    }

    public Address updateAddress(Address Address, Long id) {
        return repository.findById(id)
                .map(AddressOrig -> {
                    AddressOrig.setStreet(Address.getStreet());
                    AddressOrig.setStreetNumber(Address.getStreetNumber());
                    AddressOrig.setCity(Address.getCity());
                    AddressOrig.setCountry(Address.getCountry());
                    return repository.save(AddressOrig);
                })
                .orElseGet(() -> repository.save(Address));
    }

    public MessageResponse deleteAddress(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Address " + id + " deleted");
    }
}
