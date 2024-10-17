package com.czavala.productmanagementsystem.services.impl;

import com.czavala.productmanagementsystem.dto.cart.AddProductToCartRequest;
import com.czavala.productmanagementsystem.dto.cart.CartDto;
import com.czavala.productmanagementsystem.exceptions.ResourceNotFoundException;
import com.czavala.productmanagementsystem.mapper.CartMapper;
import com.czavala.productmanagementsystem.persistance.Utils.CartStatus;
import com.czavala.productmanagementsystem.persistance.entities.Product;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.persistance.entities.cart.Cart;
import com.czavala.productmanagementsystem.persistance.entities.cart.CartItem;
import com.czavala.productmanagementsystem.persistance.repository.CartItemRepository;
import com.czavala.productmanagementsystem.persistance.repository.CartRepository;
import com.czavala.productmanagementsystem.persistance.repository.ProductRepository;
import com.czavala.productmanagementsystem.services.CartService;
import com.czavala.productmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Override
    public CartDto getCurrentUserCart(String username) {
        User user = userService.findByUsername(username).get();
        Cart cartFromDB = this.getOrCreateCartByUserId(user);
        return cartMapper.mapToCartDto(cartFromDB);
    }

    @Override
    public CartDto addItemToCart(String username, AddProductToCartRequest addProductRequest) {

        Long productId = addProductRequest.getProductId();
        Integer productQuantity = addProductRequest.getQuantity();

        // Obtiene user autenticado
        User user = userService.findByUsername(username).get();

        // Obtener carrito del user, o crear uno si aún no tiene uno
        Cart userCart = getOrCreateCartByUserId(user);

        // Obtener producto segun id
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. Product id: " + productId));

        // Crea item para agregar al carrito
        CartItem cartItem = new CartItem();
        cartItem.setCart(userCart);
        cartItem.setProduct(product);
        cartItem.setQuantity(productQuantity);
        // todo - crear repositorio para guardar CartItem (aunque no sé si es necesario) -> (actualizacion 24/06) SI ERA NECESARIO
        // todo: De lo contrario, no guarda los items en el carrito
        cartItemRepository.save(cartItem);

        // Agrega a la lista de items del cart, el CartItem recien creado
        userCart.getCartItems().add(cartItem);

        // Guarda Cart en DB
        cartRepository.save(userCart);

        return cartMapper.mapToCartDto(userCart);
    }

    @Override
    public CartDto removeItemFromCart(String username, Long productId) {

        User user = userService.findByUsername(username).get();

        // Obtiene cart del usuario autenticado (sobre el que se quiere eliminar un producto)
        Cart cartFromDB = getCartByUser(user);

        // Obtiene los items existentes en el cart
        // Luego busca el item en el cart, segun el id del item (producto)
        CartItem cartItem = cartFromDB.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart. Item id: " + productId));

        cartFromDB.getCartItems().remove(cartItem);

        // elimina el cart item del repositorio (aun no definido si esto va o no)
        // Esto debe ir, ya que de lo contrario, el cart no se actualiza
        cartItemRepository.delete(cartItem);

        cartRepository.save(cartFromDB);
        return cartMapper.mapToCartDto(cartFromDB);
    }

    // Busca Cart de usuario autenticado
    // Si no encuentra un Cart, crea uno por defecto
    private Cart getOrCreateCartByUserId(User user) {
        return cartRepository.findByUserIdAndStatusNew(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setStatus(CartStatus.NEW);
                    newCart.setCreatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });
    }

    // Sólo busca un cart en DB, segun el user autenticado
    private Cart getCartByUser(User user) {
        return cartRepository.findByUserIdAndStatusNew(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found. User id: " + user.getId()));
    }
}
