package com.example.commonProject.repository;

import com.example.commonProject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductId(long cartId, long productId);
}
