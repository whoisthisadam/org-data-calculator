package com.chumachenko.coursework.repository.orgdata;

import com.chumachenko.coursework.domain.orgdata.OrgData;
import com.chumachenko.coursework.repository.CRUDRepository;

import java.util.List;

public interface OrgDataRepository extends CRUDRepository<Long, OrgData>, AutoCloseable {

    Boolean isThisOrgPresent(Long orgId);

    OrgData updateData(OrgData orgData);

//    OrgDataLiquidityDto insertInfoForLiquidity(OrgDataLiquidityDto orgDataLiquidityDto);
//
//    OrgDataSolvencyDto insertInfoForSolvency(OrgDataSolvencyDto orgDataSolvencyDto);

}
