package com.czavala.productmanagementsystem.persistance.entities.order;

import com.czavala.productmanagementsystem.persistance.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @CreatedDate
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Obtiene precio x cantidad de cada OrderProduct
    // Luego realiza sumatoria del total de cada OrderProduct
    // Retorna el total del valor de la Order
    @Transient
    public Long getTotalOrder() {
        Long total = 0L;
        for (OrderProduct op : this.orderProducts) {
            total += op.getTotalPrice();
        }
        return total;
    }

    //
    // Obtiene la cantidad de productos de la orden
    @Transient
    public Integer getNumberOfProducts() {
        return this.orderProducts.size();
    }
}
