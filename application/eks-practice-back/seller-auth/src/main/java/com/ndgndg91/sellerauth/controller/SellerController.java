package com.ndgndg91.sellerauth.controller;

import static com.ndgndg91.sellerauth.global.ApiResult.body;

import com.ndgndg91.sellerauth.global.ApiResult;
import com.ndgndg91.sellerauth.global.ErrorCode;
import com.ndgndg91.sellerauth.global.ServiceException;
import com.ndgndg91.sellerauth.controller.request.CreateSellerRequest;
import com.ndgndg91.sellerauth.controller.response.SellerResponse;
import com.ndgndg91.sellerauth.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SellerController {

    private final SellerRepository sellerRepository;

    @GetMapping("/apis/sellers/{id}")
    public ResponseEntity<ApiResult<SellerResponse>> findById(
        @PathVariable long id
    ) {
        final var seller = sellerRepository.findById(id)
            .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND.value(),
                ErrorCode.ESP1404));
        return ResponseEntity.ok(body(new SellerResponse(seller)));
    }

    @PostMapping("/apis/sellers")
    public ResponseEntity<Void> create(
        @RequestBody @Valid final CreateSellerRequest request
    ) {
        var save = sellerRepository.save(request.toSeller());
        return ResponseEntity
            .created(URI.create("/sellers/" + save.getId()))
            .build();
    }
}
