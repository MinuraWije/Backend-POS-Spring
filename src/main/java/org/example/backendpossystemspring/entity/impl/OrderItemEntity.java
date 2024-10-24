package org.example.backendpossystemspring.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for the order item entry

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OrderEntity order; // Reference to the OrderEntity

    @ManyToOne
    @JoinColumn(name = "itemCode", nullable = false)
    private ItemEntity item; // Reference to the ItemEntity

    private Integer quantity; // Quantity of the item in the order
}