package ch.ilv.ebanking;

import ch.ilv.ebanking.model.Address;
import ch.ilv.ebanking.controller.AddressController;
import ch.ilv.ebanking.repository.AddressRepository;
import ch.ilv.ebanking.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AddressControllerTest {
    private AddressService addressServiceMock;

    private AddressController addressControllerMock = mock(AddressController.class);
    private final AddressRepository addressRepositoryMock = mock(AddressRepository.class);

    private final Address addressMock = mock(Address.class);

    @BeforeEach
    void setUp() {
        addressServiceMock = new AddressService(addressRepositoryMock);
    }

    @Test
    void all() {
        when(addressRepositoryMock.findByOrderByCityAscStreetAsc()).thenReturn(List.of(addressMock));
        addressControllerMock.all();
        verify(addressControllerMock, times(1)).all();
    }

    @Test
    void one() {
        when(addressRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(addressMock));
        addressControllerMock.one(any());
        verify(addressControllerMock, times(1)).one(any());
    }

    @Test
    void newAddress() {
        addressControllerMock.newAddress(any());
        verify(addressControllerMock, times(1)).newAddress(any());
    }

    @Test
    void updateAddress() {
        addressControllerMock.updateAddress(addressMock, addressMock.getId());
        verify(addressControllerMock, times(1)).updateAddress(addressMock, addressMock.getId());
    }

    @Test
    void deleteAddress() {
        addressControllerMock.deleteAddress(any());
        verify(addressControllerMock, times(1)).deleteAddress(any());
    }
}