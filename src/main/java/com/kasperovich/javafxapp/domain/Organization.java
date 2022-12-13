package com.kasperovich.javafxapp.domain;

import com.kasperovich.javafxapp.domain.enums.OrgType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organization {

    Long id;

    OrgType type;

    String name;

    Integer numberOfEmployees;

    Timestamp creationDate;

    Timestamp modificationDate;

    Long userId;

    Boolean isDeleted;

    Double solvency;

    Double liquidity;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
                this, ToStringStyle.JSON_STYLE
        );
    }
}
