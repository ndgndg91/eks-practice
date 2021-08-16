package com.ndgndg91.batch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class IndeedItems {
    private final List<IndeedItem> items;
}
