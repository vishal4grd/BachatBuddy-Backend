package com.Mystartup.BacahatBuddy.Service;

import com.Mystartup.BacahatBuddy.DTO.AddressRequestDTO;
import com.Mystartup.BacahatBuddy.DTO.AddressResponseDTO;
import com.Mystartup.BacahatBuddy.Exception.ResourceNotFoundException;
import com.Mystartup.BacahatBuddy.Model.Address;
import com.Mystartup.BacahatBuddy.Model.User;
import com.Mystartup.BacahatBuddy.Repository.AddressRepository;
import com.Mystartup.BacahatBuddy.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository,
                          UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public List<AddressResponseDTO> getAddressesByUser(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream().map(this::toDTO).toList();
    }

    @Transactional
    public AddressResponseDTO addAddress(Long userId, AddressRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Address address = new Address();
        fromDTO(dto, address);
        address.setUser(user);

        // handle default address logic
        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            clearExistingDefault(userId);
            address.setDefault(true);   // ✅ FIXED
        }

        Address saved = addressRepository.save(address);
        return toDTO(saved);
    }

    @Transactional
    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO dto) {
        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        fromDTO(dto, existing);

        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            clearExistingDefault(existing.getUser().getId(), id);
            existing.setDefault(true);   // ✅ FIXED
        }

        Address saved = addressRepository.save(existing);
        return toDTO(saved);
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        addressRepository.delete(existing);
    }

    private void clearExistingDefault(Long userId) {
        addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .ifPresent(addr -> {
                    addr.setDefault(false);   // ✅ FIXED
                    addressRepository.save(addr);
                });
    }

    private void clearExistingDefault(Long userId, Long excludeId) {
        List<Address> addresses = addressRepository.findByUserIdAndIdNot(userId, excludeId);
        addresses.stream()
                .filter(a -> Boolean.TRUE.equals(a.getDefault()))
                .forEach(addr -> {
                    addr.setDefault(false);   // ✅ FIXED
                    addressRepository.save(addr);
                });
    }

    private AddressResponseDTO toDTO(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setName(address.getName());
        dto.setPhone(address.getPhone());
        dto.setLine1(address.getLine1());
        dto.setLine2(address.getLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPincode(address.getPincode());
        dto.setIsDefault(address.getDefault());   // ✅ FIXED
        dto.setUserId(address.getUser().getId());
        return dto;
    }

    private void fromDTO(AddressRequestDTO dto, Address address) {
        address.setName(dto.getName());
        address.setPhone(dto.getPhone());
        address.setLine1(dto.getLine1());
        address.setLine2(dto.getLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());

        if (dto.getIsDefault() != null) {
            address.setDefault(dto.getIsDefault());   // ✅ FIXED
        }
    }
}