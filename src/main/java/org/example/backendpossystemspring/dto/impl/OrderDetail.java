package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.dto.SuperDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetail implements SuperDTO {
    /*private String orderId;
    private String customerId;
    private String itemId;
    private int qty;
    private double unitPrice;*/
    private long id;
    private String orderId;
    private String itemId;
    private int qty;
}
