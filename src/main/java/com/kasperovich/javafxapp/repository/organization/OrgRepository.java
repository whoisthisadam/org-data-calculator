package com.kasperovich.javafxapp.repository.organization;

import com.kasperovich.javafxapp.domain.Organization;
import com.kasperovich.javafxapp.exception.RecurringOrgNameException;
import com.kasperovich.javafxapp.repository.CRUDRepository;

import java.util.List;

public interface OrgRepository extends CRUDRepository<Long, Organization> , AutoCloseable{

    List<Organization>findAllByUserId(Long userId);

    Long findNumberOfOrgsOfUser(Long userId);

    Organization create(Organization object);

    void deleteByUserIdAndName(Long userId, String name);

    Double updateLiquidity(Double liquidity, Long id);

    Double updateSolvency(Double solvency, Long id);

    Organization findByUserIdAndName(Long userId, String name);
}
