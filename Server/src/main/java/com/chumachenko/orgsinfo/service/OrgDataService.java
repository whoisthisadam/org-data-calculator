package com.chumachenko.orgsinfo.service;

import com.chumachenko.orgsinfo.repository.orgdata.OrgDataRepoImpl;
import com.chumachenko.orgsinfo.repository.orgdata.OrgDataRepository;
import entities.OrgData;

public class OrgDataService {

    OrgDataRepository orgDataRepository=new OrgDataRepoImpl();

    public boolean isThisOrgPresent(Long orgId){
        return orgDataRepository.isThisOrgPresent(orgId);
    }

    public OrgData updateOrgData(OrgData orgData){
        return orgDataRepository.updateData(orgData);
    }

    public OrgData createOrgData(OrgData orgData){
        return orgDataRepository.create(orgData);
    }

}
