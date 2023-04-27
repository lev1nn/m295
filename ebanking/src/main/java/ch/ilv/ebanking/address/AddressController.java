package ch.ilv.ebanking.address;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name="bearerAuth")
@RestController
public class AddressController {
    public final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
