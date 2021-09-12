package com.ndgndg91.sellerauth.controller;

import com.ndgndg91.sellerauth.JwtResolver;
import com.ndgndg91.sellerauth.controller.request.LoginRequest;
import com.ndgndg91.sellerauth.controller.response.LoginResponse;
import com.ndgndg91.sellerauth.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SellerRepository sellerRepository;

    private final JwtResolver jwtResolver;

    @PostMapping("/seller-auth/sellers/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        final var seller = sellerRepository.login(request.getIdentifier(), request.getPassword())
                .orElseThrow();

        final var refreshToken = jwtResolver.issueRefreshToken(seller.getId());
        final var accessToken = jwtResolver.issueAccessToken(seller.getId(), seller.firstStoreId(), seller.getFullName());

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }
}
