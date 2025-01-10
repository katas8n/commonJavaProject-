package com.example.commonProject.repository;

import com.example.commonProject.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProfileId(long profileId);
}
