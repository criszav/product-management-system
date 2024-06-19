package com.czavala.productmanagementsystem.mapper;

import com.czavala.productmanagementsystem.dto.cart.CartDto;
import com.czavala.productmanagementsystem.dto.cart.CartItemDto;
import com.czavala.productmanagementsystem.persistance.entities.cart.Cart;
import com.czavala.productmanagementsystem.persistance.entities.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CartMapper {

    public CartDto mapToCartDto(Cart cartFromDB) {

        CartDto cartDto = new CartDto();
        cartDto.setId(cartFromDB.getId());
        cartDto.setUserId(cartFromDB.getUser().getId());
        cartDto.setCartItems(mapToCartItemDto(cartFromDB.getCartItems()));
        cartDto.setNumberOfProducts(cartFromDB.getNumberOfProducts());
        cartDto.setTotalCart(cartFromDB.getTotalCart());

        return cartDto;
    }

    private List<CartItemDto> mapToCartItemDto(List<CartItem> cartItems) {

        List<CartItemDto> cartItemDtoList = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setCartId(cartItem.getCart().getId());
            cartItemDto.setProductId(cartItem.getProduct().getId());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setTotalPrice(cartItem.getTotalPrice());

            cartItemDtoList.add(cartItemDto);
        }
        return cartItemDtoList;
    }
}
