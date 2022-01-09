package com.ndgndg91.buyerauth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BuyerController {

    @GetMapping("/buyer-auth")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok buyer auth application");
    }
    
    @GetMapping("/buyer-auth/{name}")
    public ResponseEntity<String> name(@PathVariable final String name) {
        return ResponseEntity.ok("ok buyer auth " + name);
    }
}
