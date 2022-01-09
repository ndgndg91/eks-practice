package com.ndgndg91.sellerauth.domain.vo;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Agreement {
    private boolean requiredAgreement;
    private LocalDateTime requiredAgreeAt;

    private boolean marketingAgreement;
    private LocalDateTime marketingAgreeAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Agreement agreement = (Agreement) o;

        if (requiredAgreement != agreement.requiredAgreement) {
            return false;
        }
        if (marketingAgreement != agreement.marketingAgreement) {
            return false;
        }
        if (!Objects.equals(requiredAgreeAt, agreement.requiredAgreeAt)) {
            return false;
        }
        return Objects.equals(marketingAgreeAt, agreement.marketingAgreeAt);
    }

    @Override
    public int hashCode() {
        int result = (requiredAgreement ? 1 : 0);
        result = 31 * result + (requiredAgreeAt != null ? requiredAgreeAt.hashCode() : 0);
        result = 31 * result + (marketingAgreement ? 1 : 0);
        result = 31 * result + (marketingAgreeAt != null ? marketingAgreeAt.hashCode() : 0);
        return result;
    }
}
