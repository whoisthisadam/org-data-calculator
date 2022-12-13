package com.kasperovich.javafxapp.domain.orgdata;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrgData {

    Long id;

    Long orgId;

    Double bankroll;

    Double shortInvestments;

    Double shortReceivables;

    Double shortLiabilities;

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
