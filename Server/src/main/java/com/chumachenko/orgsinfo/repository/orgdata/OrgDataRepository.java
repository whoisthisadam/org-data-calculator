package com.chumachenko.orgsinfo.repository.orgdata;

import com.chumachenko.orgsinfo.repository.CRUDRepository;
import entities.OrgData;

public interface OrgDataRepository extends CRUDRepository<Long, OrgData>, AutoCloseable {

    Boolean isThisOrgPresent(Long orgId);

    OrgData updateData(OrgData orgData);

//    OrgDataLiquidityDto insertInfoForLiquidity(OrgDataLiquidityDto orgDataLiquidityDto);
//
//    OrgDataSolvencyDto insertInfoForSolvency(OrgDataSolvencyDto orgDataSolvencyDto);

}
