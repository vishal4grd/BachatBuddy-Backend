package com.Mystartup.BacahatBuddy.Service;

import com.Mystartup.BacahatBuddy.Model.Address;
import com.Mystartup.BacahatBuddy.Model.User;
import com.Mystartup.BacahatBuddy.Repository.AddressRepository;
import com.Mystartup.BacahatBuddy.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepo;
    private final UserRepository userRepo;

    public AddressService(AddressRepository addressRepo, UserRepository userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    public List<Address> getAddresses(Long userId) {
        return addressRepo.findByUser_Id(userId); // ✅ Correct nested query
    }

    public Address addAddress(Long userId, Address address) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        address.setUser(user); // ✅ Set full User object
        return addressRepo.save(address);
    }

    public Address updateAddress(Long id, Address updated) {
        Address existing = addressRepo.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        updated.setId(id);
        updated.setUser(existing.getUser()); // ✅ Preserve user association
        return addressRepo.save(updated);
    }

    public void deleteAddress(Long id) {
        addressRepo.deleteById(id);
    }
}