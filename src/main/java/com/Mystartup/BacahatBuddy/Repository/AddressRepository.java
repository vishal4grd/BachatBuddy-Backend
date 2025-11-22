package com.Mystartup.BacahatBuddy.Repository;

import com.Mystartup.BacahatBuddy.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(Long userId); // ✅ Corrected method
}

