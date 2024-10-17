package org.example.backendpossystemspring.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendpossystemspring.entity.SuperEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity implements SuperEntity {
    @Id
    private String customerId;
    private String customerName;
    @Column(unique = true)
    private String customerEmail;
    private String customerAddress;
    @Column(unique = true)
    private String customerPhone;
    /*@OneToMany(mappedBy = "customer")
    private List<ItemEntity> items;*/
}
