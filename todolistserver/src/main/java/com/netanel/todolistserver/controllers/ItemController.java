package com.netanel.todolistserver.controllers;

import com.netanel.todolistserver.repos.ItemRepository;
import com.netanel.todolistserver.entities.Item;
import com.netanel.todolistserver.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @GetMapping("/for-user")
    public List<Item> getItemsForUser(Principal principal) {
        Long userid = Long.valueOf(principal.getName());
        List<Item> items = itemRepository.findByUserid(userid);
        return items;
    }

    @PostMapping
    Item addItem(@RequestBody Item newItem) {
        return itemRepository.save(newItem);
    }

    @PutMapping("/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setTask(newItem.getTask());
                    item.setCompleted(newItem.isCompleted());
                    return itemRepository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return itemRepository.save(newItem);
                });
    }

    @DeleteMapping("/{id}")
    void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
