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

    @GetMapping("/apis/sellers")
    public ResponseEntity<List<Seller>> findAll() {
        log.info("들어왔다야!");
        return ResponseEntity.ok(sellerRepository.findAll());
    }

    @GetMapping("/apis/sellers/{id}")
    public ResponseEntity<Seller> findById(
            @PathVariable long id
    ) {
        log.info("들어왔다야!");
        return ResponseEntity.ok(sellerRepository.findById(id).orElseThrow());
    }

    @PostMapping("/apis/sellers")
    public ResponseEntity<Seller> create() {
        log.info("들어왔다야!");
        var save = sellerRepository.save(new Seller());
        return ResponseEntity
                .created(URI.create("/apis/sellers/" + save.getIdentifier()))
                .build();
    }
}
