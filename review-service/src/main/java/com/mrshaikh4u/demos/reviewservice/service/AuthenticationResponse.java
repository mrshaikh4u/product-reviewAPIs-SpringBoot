package com.mrshaikh4u.demos.reviewservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private final String jwtToken;
}
