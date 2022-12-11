package com.kasperovich.javafxapp.domain;

import com.kasperovich.javafxapp.domain.enums.RolesType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    Long id;

    RolesType name;

    Set<User> users;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
                this, ToStringStyle.JSON_STYLE
        );
    }

}
