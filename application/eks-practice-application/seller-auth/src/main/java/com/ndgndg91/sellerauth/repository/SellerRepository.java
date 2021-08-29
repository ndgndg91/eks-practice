package com.ndgndg91.sellerauth.repository;

import com.ndgndg91.sellerauth.Seller;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class SellerRepository {
    private long current = 2L;
    private final Map<Long, Seller> repository = new HashMap<>();

    public SellerRepository() {
        var seller = new Seller(1L,
                "남동길",
                LocalDate.of(1991, 11, 7),
                "01072255198", "KTF",
                "giri", "123123",
                LocalDateTime.now(), LocalDateTime.now());
        this.repository.put(seller.getId(), seller);
    }

    public List<Seller> findAll() {
        return new ArrayList<>(repository.values());
    }

    public Optional<Seller> findById(final long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Seller save(final Seller seller) {
        seller.setId(current++);
        repository.put(seller.getId(), seller);
        return seller;
    }

    public Optional<Seller> login(String identifier, String password) {
        return repository.values()
                .stream()
                .filter(s -> s.getIdentifier().equals(identifier) && s.getPassword().equals(password))
                .findFirst();
    }
}
