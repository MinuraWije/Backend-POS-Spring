package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
    private String itemCode;
    private String itemName;
    private double unitPrice;
    private int qty;
    private double totalPrice;
}
