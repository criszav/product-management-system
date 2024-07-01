package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.cart.AddProductToCartRequest;
import com.czavala.productmanagementsystem.dto.cart.CartDto;
import com.czavala.productmanagementsystem.dto.order.OrderDto;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.CartService;
import com.czavala.productmanagementsystem.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;


    @GetMapping("/current")
    public ResponseEntity<CartDto> getCurrentUserCart(@AuthenticationPrincipal String username) {
        CartDto cart = cartService.getCurrentUserCart(username);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> addProductToCart(@AuthenticationPrincipal String username, @RequestBody AddProductToCartRequest addProductRequest) {
        CartDto cart = cartService.addItemToCart(username, addProductRequest);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@AuthenticationPrincipal String username, @PathVariable Long productId) {
        CartDto cart = cartService.removeItemFromCart(username, productId);
        return ResponseEntity.ok(cart);
    }

    // Endpoint para crear un Order a partir de un Cart (carrito de compra)
    // Se genera cuando el cliente avanza en el proceso de compra (pago, por ejemplo)
    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkoutCartToOrder(@AuthenticationPrincipal String username) {
        OrderDto order = orderService.createOrderFromCartCheckout(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
