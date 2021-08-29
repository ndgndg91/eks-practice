package com.ndgndg91.sellerauth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Store {
    private long id;
    private String name;
    private String identifier;
    private String category;

    private long sellerId;
}
