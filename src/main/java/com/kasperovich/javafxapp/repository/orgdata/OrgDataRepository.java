package com.kasperovich.javafxapp.repository.orgdata;

import com.kasperovich.javafxapp.domain.orgdata.OrgData;
import com.kasperovich.javafxapp.domain.orgdata.OrgDataLiquidityDto;
import com.kasperovich.javafxapp.domain.orgdata.OrgDataSolvencyDto;
import com.kasperovich.javafxapp.repository.CRUDRepository;

import java.util.List;

public interface OrgDataRepository extends CRUDRepository<Long,OrgData>, AutoCloseable {

    List<OrgDataLiquidityDto> findAllForLiquidityOfOrganization(Long orgId);

    List<OrgDataSolvencyDto>findAllForSolvencyOfOrganization(Long orgId);

    Boolean isThisOrgPresent(Long orgId);

    OrgData updateData(OrgData orgData);

//    OrgDataLiquidityDto insertInfoForLiquidity(OrgDataLiquidityDto orgDataLiquidityDto);
//
//    OrgDataSolvencyDto insertInfoForSolvency(OrgDataSolvencyDto orgDataSolvencyDto);

}
