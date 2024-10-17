package org.example.backendpossystemspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.dto.ItemStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item implements ItemStatus {
    private String itemCode;
    private String itemName;
    private int itemQuantity;
    private int itemPrice;
}
