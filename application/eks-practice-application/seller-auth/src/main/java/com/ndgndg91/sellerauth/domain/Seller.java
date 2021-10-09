package com.ndgndg91.sellerauth.domain;

import com.ndgndg91.sellerauth.global.ErrorCode;
import com.ndgndg91.sellerauth.global.ServiceException;
import com.ndgndg91.sellerauth.domain.vo.Agreement;
import com.ndgndg91.sellerauth.domain.vo.Birthday;
import com.ndgndg91.sellerauth.domain.vo.CellPhone;
import com.ndgndg91.sellerauth.domain.vo.Local;
import com.ndgndg91.sellerauth.domain.vo.Role;
import com.ndgndg91.sellerauth.domain.vo.Sex;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 35)
    private String identifier;

    private String password;

    @Embedded
    @AttributeOverride(name = "numbers", column = @Column(name = "cell_phone_number"))
    @AttributeOverride(name = "mobileCarrier", column = @Column(name = "mobile_carrier"))
    private CellPhone phone;

    @Embedded
    @AttributeOverride(name = "requiredAgreement", column = @Column(name = "required_agreement"))
    @AttributeOverride(name = "requiredAgreeAt", column = @Column(name = "required_agree_at"))
    @AttributeOverride(name = "marketingAgreement", column = @Column(name = "marketing_agreement"))
    @AttributeOverride(name = "marketingAgreeAt", column = @Column(name = "marketing_agree_at"))
    private Agreement agreement;

    private String fullName;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "birth_day"))
    private Birthday birthday;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Local local;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Seller(String identifier, String password, boolean marketingAgreement) {
        this.identifier = identifier;
        this.password = password;
        this.agreement = new Agreement(true, LocalDateTime.now(), marketingAgreement, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Seller seller = (Seller) o;

        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void checkPassword(String password) {
        if (this.password.equals(password)) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), ErrorCode.ESP0000);
        }
    }
}