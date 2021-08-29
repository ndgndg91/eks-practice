package com.ndgndg91.sellerauth;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Seller {

    @Setter
    private long id;
    private String fullName;
    private LocalDate birthday;
    private String cellPhoneNumber;
    private String mobileCarrier;

    private String identifier;
    private String password;

    private final List<Store> stores = new ArrayList<>();

    private LocalDateTime lastModifiedAt;
    private LocalDateTime createdAt;

    public Long firstStoreId() {
        if (stores.isEmpty()) return null;
        return stores.get(0).getId();
    }
}
