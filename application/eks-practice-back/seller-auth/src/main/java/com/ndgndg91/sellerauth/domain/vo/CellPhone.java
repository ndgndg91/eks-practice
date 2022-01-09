package com.ndgndg91.sellerauth.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class CellPhone {

    private String numbers;
    @Enumerated(EnumType.STRING)
    private MobileCarrier mobileCarrier;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CellPhone cellPhone = (CellPhone) o;

        if (!Objects.equals(numbers, cellPhone.numbers)) {
            return false;
        }
        return mobileCarrier == cellPhone.mobileCarrier;
    }

    @Override
    public int hashCode() {
        int result = numbers != null ? numbers.hashCode() : 0;
        result = 31 * result + (mobileCarrier != null ? mobileCarrier.hashCode() : 0);
        return result;
    }
}
