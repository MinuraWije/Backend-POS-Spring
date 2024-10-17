package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetail {
    private String orderId;
    private String customerId;
    private String itemId;
    private int qty;
    private double unitPrice;
}
