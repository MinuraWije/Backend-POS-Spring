package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String orderId;
    private String customerId;
    private String customerName;
    private String date;
    private double total;
    private String discount;
    private double subtotal;
}
