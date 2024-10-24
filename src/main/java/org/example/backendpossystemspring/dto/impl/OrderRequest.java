package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.dto.SuperDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest implements SuperDTO {
    private String orderId;
    private String customerId;
    private String date;
    private String customerName;
    private double total;
    private String discount;
    private double subTotal;

    private List<CartItemDto> cartItems;
}
