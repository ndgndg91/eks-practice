package com.ndgndg91.product.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EC0000("잘못된 요청입니다."),
    EC1000("title 누락"),
    EC1001("description 누락"),
    EC1002("seoTitle 누락"),
    EC1003("seoDescription 누락")
    ;

    private final String description;
}
