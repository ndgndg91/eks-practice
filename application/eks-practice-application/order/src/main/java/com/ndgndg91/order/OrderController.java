package com.ndgndg91.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok order application");
    }
}
