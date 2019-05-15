package com.rainingsince.admin.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auth {
    private String name;
    private String token;
}
