package org.example.backendpossystemspring.service;

import org.example.backendpossystemspring.dto.ItemStatus;
import org.example.backendpossystemspring.dto.impl.Item;

import java.util.List;

public interface ItemService {
    void saveItem(Item itemDTO);
    List<Item> getAllItems();
    ItemStatus getItem(String itemId);
    void deleteItem(String itemID);
    void updateItem(String itemId,Item itemDTO);
}
