package org.example.backendpossystemspring.service.impl;

import org.example.backendpossystemspring.customStatusCodes.SelectedCustomerItemOrderErrorStatus;
import org.example.backendpossystemspring.dao.ItemDao;
import org.example.backendpossystemspring.dto.ItemStatus;
import org.example.backendpossystemspring.dto.impl.Item;
import org.example.backendpossystemspring.entity.impl.ItemEntity;
import org.example.backendpossystemspring.exception.DataPersistException;
import org.example.backendpossystemspring.exception.ItemNotFoundException;
import org.example.backendpossystemspring.service.ItemService;
import org.example.backendpossystemspring.util.AppUtil;
import org.example.backendpossystemspring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private Mapping mapping;
    /*private static List<NoteDTO> noteDTOList = new ArrayList<>();

    NoteServiceImpl(){
        noteDTOList.add(new NoteDTO())
    }*/
    @Override
    public void saveItem(Item itemDTO) {
        itemDTO.setItemCode(AppUtil.generateItemId());
        ItemEntity savedNote = itemDao.save(mapping.toItemEntity(itemDTO));
        if(savedNote == null){
            throw new DataPersistException("Item not saved.");
        }
    }

    @Override
    public List<Item> getAllItems() {
        return mapping.asItemDTOList(itemDao.findAll());
    }

    @Override
    public ItemStatus getItemById(String id) {
        if (id == null || id.isEmpty()) {
            throw new DataIntegrityViolationException("Item id cannot be empty");
        } else if (itemDao.existsById(id)) {
            return mapping.toItemDTO(itemDao.getById(id));
        }
        return new SelectedCustomerItemOrderErrorStatus(2,"Item not found");
    }

    @Override
    public void deleteItem(String itemID) {
        Optional<ItemEntity> foundNote = itemDao.findById(itemID);
        if(!foundNote.isPresent()){
            throw new ItemNotFoundException("Item not found.");
        }else{
            itemDao.deleteById(itemID);
        }
    }

    @Override
    public void updateItem(String noteId, Item itemDTO) {
        Optional<ItemEntity> findNote = itemDao.findById(noteId);
        if(!findNote.isPresent()){
            throw new ItemNotFoundException("Item not found");
        }else {
            findNote.get().setItemName(itemDTO.getItemName());
            findNote.get().setItemQuantity(itemDTO.getItemQuantity());
            findNote.get().setItemPrice(itemDTO.getItemPrice());
        }
    }

    @Override
    public List<String> findSuggestions(String query) {
        List<ItemEntity> items = itemDao.findAll();

        // Filter items based on the query and return a formatted string for each item
        return items.stream()
                .filter(item -> item.getItemName().toLowerCase().contains(query.toLowerCase()))
                .map(item -> String.format(" Name: %s -Code: %s -Price: %s -QTO: %d", item.getItemName(), item.getItemCode(), item.getItemPrice(), item.getItemQuantity()))
                .collect(Collectors.toList());
    }
}
