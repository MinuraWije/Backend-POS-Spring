package org.example.backendpossystemspring.customStatusCodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.dto.CustomerStatus;
import org.example.backendpossystemspring.dto.ItemStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedCustomerItemOrderErrorStatus implements CustomerStatus, ItemStatus {
    private int statusCode;
    private String statusMessage;
}
