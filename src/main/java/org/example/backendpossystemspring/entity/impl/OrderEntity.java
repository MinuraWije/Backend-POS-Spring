package org.example.backendpossystemspring.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.entity.SuperEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Orders")
public class OrderEntity implements SuperEntity {
    @Id
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerEntity customer;

    private String orderDate;
    private Double total;
    private String discount;
    private Double subTotal;

    // Change from List<ItemEntity> to List<OrderItemEntity>
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems; // Now holds OrderItemEntity
}