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

    public Address insertAddress(Address address) {
        return repository.save(address);
    }

    public Address updateAddress(Address address, Long id) {
        return repository.findById(id)
                .map(addressOrig -> {
                    addressOrig.setStreet(address.getStreet());
                    addressOrig.setStreetNumber(address.getStreetNumber());
                    addressOrig.setCity(address.getCity());
                    addressOrig.setCountry(address.getCountry());
                    return repository.save(addressOrig);
                })
                .orElseGet(() -> repository.save(address));
    }

    public MessageResponse deleteAddress(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Address " + id + " deleted");
    }
}
