package com.rainingsince.admin.auth.controller;

import com.rainingsince.admin.auth.service.AuthService;
import com.rainingsince.admin.user.entity.User;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        return authService.login(user);
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        return ResponseBuilder.ok(authService.logout());
    }

    @GetMapping("/permissions")
    public ResponseEntity permissions() {
        return ResponseBuilder.ok(authService.permissions());
    }

}
