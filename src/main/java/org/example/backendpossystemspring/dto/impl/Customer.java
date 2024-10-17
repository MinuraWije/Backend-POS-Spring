package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.dto.CustomerStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer implements CustomerStatus {
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerPhone;
}
