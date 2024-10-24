package org.example.backendpossystemspring.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    private String itemCode;
    private String itemName;
    private int itemQuantity;
    private int itemPrice;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;
}
