package com.Mystartup.BacahatBuddy.Controller;

import com.Mystartup.BacahatBuddy.Model.Address;
import com.Mystartup.BacahatBuddy.Repository.AddressRepository;
import com.Mystartup.BacahatBuddy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin("*")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<Address> getAddressesByUser(@PathVariable Long userId) {
        return addressRepository.findAll().stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .toList();
    }

    @PostMapping("/user/{userId}")
    public Address addAddress(@PathVariable Long userId, @RequestBody Address address) {
        userRepository.findById(userId).ifPresent(address::setUser);
        return addressRepository.save(address);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Address existing = addressRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(address.getName());
            existing.setPhone(address.getPhone());
            existing.setLine1(address.getLine1());
            existing.setLine2(address.getLine2());
            existing.setCity(address.getCity());
            existing.setState(address.getState());
            existing.setPincode(address.getPincode());
            existing.setIsDefault(address.getIsDefault());
            return addressRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
    }
}
