package com.Mystartup.BacahatBuddy.Controller;

import com.Mystartup.BacahatBuddy.Model.Coupon;
import com.Mystartup.BacahatBuddy.Repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin("*")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @GetMapping
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @PostMapping
    public Coupon addCoupon(@RequestBody Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon existing = couponRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(coupon.getCode());
            existing.setTitle(coupon.getTitle());
            existing.setDiscountType(coupon.getDiscountType());
            existing.setDiscountValue(coupon.getDiscountValue());
            existing.setMinOrder(coupon.getMinOrder());
            existing.setMaxDiscount(coupon.getMaxDiscount());
            existing.setValidFrom(coupon.getValidFrom());
            existing.setValidTo(coupon.getValidTo());
            existing.setIsActive(coupon.getIsActive());
            return couponRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable Long id) {
        couponRepository.deleteById(id);
    }
}
