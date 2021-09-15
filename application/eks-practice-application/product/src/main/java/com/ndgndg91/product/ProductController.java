package com.ndgndg91.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {

    @GetMapping("/product")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok product application");
    }
    
    @GetMapping("/product/{name}")
    public ResponseEntity<String> name(@PathVariable final String name) {
        return ResponseEntity.ok("ok product auth " + name);
    }
}
