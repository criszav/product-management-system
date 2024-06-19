package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.cart.AddProductToCartRequest;
import com.czavala.productmanagementsystem.dto.cart.CartDto;
import com.czavala.productmanagementsystem.persistance.entities.User;

public interface CartService {

    CartDto getCurrentUserCart(User user);

    CartDto addItemToCart(User user, AddProductToCartRequest addProductRequest);

    CartDto removeItemFromCart(User user, Long productId);
}
