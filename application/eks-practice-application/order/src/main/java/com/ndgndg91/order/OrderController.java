package com.ndgndg91.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class OrderController {

    @GetMapping("/order")
    public ResponseEntity<String> ok() {
        var x = 0.0001;
        for (var i = 0; i <= 1000000; i++) {
            x += Math.sqrt(x);
        }
        return ResponseEntity.ok("ok order application" + x);
    }
    
    @GetMapping("/order/{name}")
    public ResponseEntity<String> name(@PathVariable final String name) {
        return ResponseEntity.ok("ok order " + name);
    }
}
