package com.ndgndg91.sellerauth.controller;

import com.ndgndg91.sellerauth.controller.request.SignUpRequest;
import com.ndgndg91.sellerauth.global.ErrorCode;
import com.ndgndg91.sellerauth.global.JwtResolver;
import com.ndgndg91.sellerauth.global.ServiceException;
import com.ndgndg91.sellerauth.controller.request.SignInRequest;
import com.ndgndg91.sellerauth.controller.response.LoginResponse;
import com.ndgndg91.sellerauth.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SellerRepository sellerRepository;

    private final JwtResolver jwtResolver;

    @PostMapping("/apis/sellers/sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody final SignInRequest request) {
        final var seller = sellerRepository.findByIdentifier(request.getIdentifier())
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND.value(), ErrorCode.ESP1404));
        seller.checkPassword(request.getPassword());

        final var refreshToken = jwtResolver.issueRefreshToken(seller.getId());
        final var accessToken = jwtResolver.issueAccessToken(seller.getId(), seller.getFullName());

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    @PostMapping("/apis/sellers/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid final SignUpRequest request) {
        return ResponseEntity.ok().build();
    }
}
