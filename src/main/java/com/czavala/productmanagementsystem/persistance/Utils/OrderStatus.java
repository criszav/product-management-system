package com.czavala.productmanagementsystem.persistance.Utils;

public enum OrderStatus {

    PROCESSING, // Cuando se realiza el checkout del Cart, se crea la Order, la cual esta siendo procesada
    SHIPPED, // Cuando se confirma la Order (ya fue pagada), y ha sido enviada al cliente
    DELIVERED, // Cuando se confirma que la Order ya fue entregada el cliente
    CANCELLED, // Cuando el cliente cancela la Order o existe algun problema con la Order
    COMPLETED // Cuando el proceso fue terminado con exito, no hya mas acciones a realizar
}
