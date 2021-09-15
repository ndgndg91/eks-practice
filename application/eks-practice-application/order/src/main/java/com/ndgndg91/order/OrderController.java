package com.ndgndg91.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class OrderController {

    @GetMapping("/order")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok order application");
    }
    
    @GetMapping("/order/{name}")
    public ResponseEntity<String> name(@PathVariable final String name) {
        return ResponseEntity.ok("ok order " + name);
    }
}
