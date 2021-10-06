package com.ndgndg91.sellerauth.domain.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Birthday {
    private LocalDate day;

    public Birthday(String day) {
        this.day = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Birthday birthday = (Birthday) o;

        return Objects.equals(day, birthday.day);
    }

    @Override
    public int hashCode() {
        return day != null ? day.hashCode() : 0;
    }
}
