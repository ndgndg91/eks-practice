package com.ndgndg91.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/product")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok product application");
    }
}
