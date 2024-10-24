package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.dto.SuperDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements SuperDTO {
    private String orderId;
    private Customer customerId;
    private String date;
    private double total;
    private String discount;
    private double subtotal;
    private List<OrderDetail> items;
}
