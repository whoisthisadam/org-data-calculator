package com.chumachenko.coursework.repository.orgdata;

import com.chumachenko.coursework.domain.orgdata.OrgData;
import com.chumachenko.coursework.repository.CRUDRepository;
import com.chumachenko.coursework.domain.orgdata.OrgDataLiquidityDto;
import com.chumachenko.coursework.domain.orgdata.OrgDataSolvencyDto;

import java.util.List;

public interface OrgDataRepository extends CRUDRepository<Long, OrgData>, AutoCloseable {

    List<OrgDataLiquidityDto> findAllForLiquidityOfOrganization(Long orgId);

    List<OrgDataSolvencyDto>findAllForSolvencyOfOrganization(Long orgId);

    Boolean isThisOrgPresent(Long orgId);

    OrgData updateData(OrgData orgData);

//    OrgDataLiquidityDto insertInfoForLiquidity(OrgDataLiquidityDto orgDataLiquidityDto);
//
//    OrgDataSolvencyDto insertInfoForSolvency(OrgDataSolvencyDto orgDataSolvencyDto);

}
