package ch.ilv.ebanking.address;

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
public class AddressController {
    public final AddressService AddressService;

    public AddressController(AddressRepository AddressRepository, AddressService AddressService) {
        this.AddressService = AddressService;
    }

    @GetMapping("api/Address")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Address>> all() {
        List<Address> result = AddressService.getAddresses();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/Address/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Address> one(@PathVariable Long id) {
        Address Address = AddressService.getAddress(id);
        return new ResponseEntity<>(Address, HttpStatus.OK);
    }

    @PostMapping("api/Address")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Address> newAddress(@Valid @RequestBody Address Address) {
        Address savedAddress = AddressService.insertAddress(Address);
        return new ResponseEntity<>(savedAddress, HttpStatus.OK);
    }

    @PutMapping("api/Address/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address Address, @PathVariable Long id) {
        Address savedAddress = AddressService.updateAddress(Address, id);
        return new ResponseEntity<>(savedAddress, HttpStatus.OK);
    }

    @DeleteMapping("api/Address/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteAddress(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(AddressService.deleteAddress(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
