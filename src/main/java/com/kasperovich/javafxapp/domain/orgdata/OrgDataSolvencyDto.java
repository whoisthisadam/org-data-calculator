package com.kasperovich.javafxapp.domain.orgdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrgDataSolvencyDto {

    Double intangibleAssets;

    Double mainAssets;

    Double prodReverses;

    Double unfinishedProduction;

    Double finishedProducts;

    Double borrowedFunds;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
                this, ToStringStyle.JSON_STYLE
        );
    }

}
