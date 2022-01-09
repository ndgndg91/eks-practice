package com.ndgndg91.batch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@RequiredArgsConstructor
@ToString
public class IndeedItem {
    private final String id;
    private final String title;
    private final String companyName;
    private final String companyLocation;
    private final String salary;
    private final String summary;
}
