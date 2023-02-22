package com.netanel.todolistserver.controllers;

import com.netanel.todolistserver.entities.User;
import com.netanel.todolistserver.repos.UserRepository;
import com.netanel.todolistserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String home(Principal principal) {
        return "Hello " + principal.getName() + "!";
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        String token = tokenService.generateAuthToken(authentication);
        return token;
    }

    @PostMapping("/register")
    public String register(@RequestBody User credentials) {
        if (credentials.getUsername().length() < 2 || credentials.getPassword().length() < 5)
            return "Invalid Username or Password. Username min length: 2, Password min length: 5.";

        if (!isValidEmail(credentials.getEmail())) {
            return "Invalid email format.";
        }

        User user = userRepository.findByEmail(credentials.getEmail());
        if(user != null)
            return "A user with the given email is already exists.";

        user = new User();
        user.setUsername(credentials.getUsername());
        user.setEmail(credentials.getEmail());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        user = userRepository.save(user);

        return tokenService.generateToken(user.getId());
    }

    @PostMapping("/login")
    public String login(@RequestBody User credentials) {
        if (credentials.getPassword().length() < 5)
            return "Invalid Username or Password.";

        if (!isValidEmail(credentials.getEmail())) {
            return "Invalid email format.";
        }

        User user = userRepository.findByEmail(credentials.getEmail());
        if (user == null) {
            return "A user with the given email is not exists.";
        }

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return "Invalid email or password.";
        }

        return tokenService.generateToken(user.getId());
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // Regular expression to match email format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}