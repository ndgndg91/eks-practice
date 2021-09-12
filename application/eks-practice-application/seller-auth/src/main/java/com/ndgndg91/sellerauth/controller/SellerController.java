package com.ndgndg91.sellerauth.controller;

import com.ndgndg91.sellerauth.Seller;
import com.ndgndg91.sellerauth.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SellerController {
    private final SellerRepository sellerRepository;

    @GetMapping("/seller-auth")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok seller-auth application");
    }

    @GetMapping("/seller-auth/sellers")
    public ResponseEntity<List<Seller>> findAll() {
        return ResponseEntity.ok(sellerRepository.findAll());
    }

    @GetMapping("/seller-auth/sellers/{id}")
    public ResponseEntity<Seller> findById(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(sellerRepository.findById(id).orElseThrow());
    }

    @PostMapping("/seller-auth/sellers")
    public ResponseEntity<Seller> create() {
        var save = sellerRepository.save(new Seller());
        return ResponseEntity
                .created(URI.create("/seller-auth/sellers/" + save.getIdentifier()))
                .build();
    }
}
