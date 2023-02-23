package com.netanel.todolistserver.controllers;

import com.netanel.todolistserver.entities.User;
import com.netanel.todolistserver.repos.UserRepository;
import com.netanel.todolistserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<String> home(Principal principal) {
        return ResponseEntity.ok("Hello " + principal.getName() + "!");
    }

    @PostMapping("/token")
    public ResponseEntity<String> token(Authentication authentication) {
        String token = tokenService.generateAuthToken(authentication);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User credentials) {
        if (credentials.getUsername().length() < 2 || credentials.getPassword().length() < 5)
            return ResponseEntity.badRequest().body("Invalid Username or Password. Username min length: 2, Password min length: 5.");

        if (!isValidEmail(credentials.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        User user = userRepository.findByEmail(credentials.getEmail());
        if(user != null)
            return ResponseEntity.badRequest().body("A user with the given email already exists.");

        user = new User();
        user.setUsername(credentials.getUsername());
        user.setEmail(credentials.getEmail());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        user = userRepository.save(user);

        String token = tokenService.generateToken(user.getId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User credentials) {
        if (credentials.getPassword().length() < 5)
            return ResponseEntity.badRequest().body("Invalid Username or Password.");

        if (!isValidEmail(credentials.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        User user = userRepository.findByEmail(credentials.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("A user with the given email does not exist.");
        }

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }

        String token = tokenService.generateToken(user.getId());
        return ResponseEntity.ok(token);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // Regular expression to match email format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}