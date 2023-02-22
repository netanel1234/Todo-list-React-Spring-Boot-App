package com.netanel.todolistserver.config;

import com.netanel.todolistserver.entities.Item;
import com.netanel.todolistserver.entities.User;
import com.netanel.todolistserver.repos.ItemRepository;
import com.netanel.todolistserver.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

//    @Bean
//    CommandLineRunner initDatabase(UserRepository userRepository, ItemRepository itemRepository) {
//        return args -> {
//            log.info("Preloading " + userRepository.save((new User("Aa", "Aa@gmail.com", "12345"))));
//            log.info("Preloading " + userRepository.save((new User("Bb", "Bb@gmail.com", "67890"))));
//            log.info("Preloading " + itemRepository.save((new Item(1L, "Task A"))));
//            log.info("Preloading " + itemRepository.save((new Item(1L, "Task B"))));
//            log.info("Preloading " + itemRepository.save((new Item(1L, "Task C"))));
//            log.info("Preloading " + itemRepository.save((new Item(2L, "Task D"))));
//            log.info("Preloading " + itemRepository.save((new Item(2L, "Task E"))));
//        };
//    }

}