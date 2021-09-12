package com.ndgndg91.buyerauth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyerController {

    @GetMapping("/buyer-auth")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("ok buyer auth application");
    }
}
