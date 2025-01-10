package com.example.commonProject.service;

import com.example.commonProject.dto.ProductDto;
import com.example.commonProject.entity.Cart;
import com.example.commonProject.entity.CartItem;
import com.example.commonProject.entity.Product;
import com.example.commonProject.exception.CartException;
import com.example.commonProject.repository.CartItemRepository;
import com.example.commonProject.repository.CartRepository;
import com.example.commonProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void addProduct(long cartId, long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new CartException("Product not found"));

        CartItem existingCartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItem newCartItem = new CartItem();

            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);

            cart.getItems().add(newCartItem);
        }

        cartRepository.save(cart);
    }

    public void deleteProduct(long cartId, long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart not found"));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (cartItem != null) {
            cart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            throw new CartException("Product not found in cart");
        }

        cartRepository.save(cart);
    }

    public List<ProductDto> getProducts(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart not found"));

        return cart.getItems().stream()
                .map(CartItem::getProduct)
                .filter(Objects::nonNull)
                .map(product -> new ProductDto(product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl()))
                .toList();
    }

    public double getTotalPrice(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart not found"));

        return cart.getItems().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
    }
}
