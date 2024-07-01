package com.czavala.productmanagementsystem.services.impl;

import com.czavala.productmanagementsystem.dto.order.OrderDto;
import com.czavala.productmanagementsystem.exceptions.ResourceNotFoundException;
import com.czavala.productmanagementsystem.mapper.OrderMapper;
import com.czavala.productmanagementsystem.persistance.Utils.CartStatus;
import com.czavala.productmanagementsystem.persistance.Utils.OrderStatus;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.persistance.entities.cart.Cart;
import com.czavala.productmanagementsystem.persistance.entities.order.Order;
import com.czavala.productmanagementsystem.persistance.entities.order.OrderProduct;
import com.czavala.productmanagementsystem.persistance.repository.CartRepository;
import com.czavala.productmanagementsystem.persistance.repository.OrderRepository;
import com.czavala.productmanagementsystem.services.OrderService;
import com.czavala.productmanagementsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;

    @Override
    public OrderDto createOrderFromCartCheckout(String username) {

        // Encuentra al user autenticado, segun su username
        User user = userService.findByUsername(username).get();

        // Busca cart del usuario autenticado
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found. User id: " + user.getId()));

        // Crea una nueva Order
        Order newOrder = new Order();
        newOrder.setUser(user);

        // Crear el detalle de cada producto de la Order (segun los items del carrito de compras)
        List<OrderProduct> orderProducts = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProduct(cartItem.getProduct());
                    orderProduct.setQuantity(cartItem.getQuantity());
                    orderProduct.setOrder(newOrder);
                    return orderProduct;
                })
                .collect(Collectors.toList());

        newOrder.setCart(cart);
        newOrder.setOrderProducts(orderProducts);
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setStatus(OrderStatus.PROCESSING);

        // Guarda la orden en DB
        orderRepository.save(newOrder);
        // orderProductRepository.saveAll(orderProducts); // todo: es posible guardar los produtos de la orden, pero aún no esta definido

        // Elimina carrito desde DB - todo: ¿será buena idea eliminar el carrito cuando este se convierte en una Order?
//        cartRepository.delete(cart);
        cart.setStatus(CartStatus.PROCESSED);
        cartRepository.save(cart);

        return orderMapper.mapToOrderDto(newOrder);
    }

    @Override
    public OrderDto getOrderById(User user, Long orderId) {
        Order orderFromDB = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found. Order id: " + orderId));
        return orderMapper.mapToOrderDto(orderFromDB);
    }

    @Override
    public List<OrderDto> getOrdersByUser(User user) {
        List<Order> orderList = orderRepository.findByUserId(user.getId());
        return orderList.stream()
                .map(order -> orderMapper.mapToOrderDto(order))
                .toList();
    }
}
