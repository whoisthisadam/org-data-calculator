package com.chumachenko.coursework.domain.orgdata;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrgDataLiquidityDto {

    Double bankroll;

    Double shortInvestments;

    Double shortReceivables;

    Double shortLiabilities;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
                this, ToStringStyle.JSON_STYLE
        );
    }

}
