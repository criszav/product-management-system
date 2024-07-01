package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.cart.AddProductToCartRequest;
import com.czavala.productmanagementsystem.dto.cart.CartDto;

public interface CartService {

    CartDto getCurrentUserCart(String username);

    CartDto addItemToCart(String username, AddProductToCartRequest addProductRequest);

    CartDto removeItemFromCart(String username, Long productId);
}
