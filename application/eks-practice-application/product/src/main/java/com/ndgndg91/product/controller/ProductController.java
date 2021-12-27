package com.ndgndg91.product.controller;

import com.ndgndg91.product.controller.dto.request.CreateProductRequest;
import com.ndgndg91.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/apis/products")
    public ResponseEntity<Void> create(
            @RequestBody @Valid final CreateProductRequest request
    ) {
        final var product = productService.create(request);
        return ResponseEntity.created(URI.create("/apis/products/" + product.getId()))
                .build();
    }

    @GetMapping("/apis/products/{id}")
    public ResponseEntity<?> findById(
            @PathVariable final long id
    ) {
        final var product = productService.findById(id).orElseThrow();
        return ResponseEntity.ok().build();
    }
}
