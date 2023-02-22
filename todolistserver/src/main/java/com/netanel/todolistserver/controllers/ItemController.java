package com.netanel.todolistserver.controllers;

import com.netanel.todolistserver.repos.ItemRepository;
import com.netanel.todolistserver.entities.Item;
import com.netanel.todolistserver.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/for-user")
    public List<Item> getItemsForUser(@AuthenticationPrincipal UserDetails userDetails) {
        Long userid = Long.valueOf(userDetails.getUsername());
        List<Item> items = itemRepository.findByUserid(userid);
        return items;
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item newItem) {
        Item item = itemRepository.save(newItem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getId())
                .toUri();
        return ResponseEntity.created(location).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setTask(newItem.getTask());
                    item.setCompleted(newItem.isCompleted());
                    Item updatedItem = itemRepository.save(item);
                    return ResponseEntity.ok().body(updatedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
