package com.chumachenko.coursework.domain;

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
public class User {

    Long id;

    String firstName;

    String lastName;

    String email;

    Timestamp creationDate;

    Timestamp modificationDate;

    Boolean isDeleted;

    String password;

    Long roleId;

    public User(String firstName, String lastName, String email, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
                this, ToStringStyle.JSON_STYLE
        );
    }


}
