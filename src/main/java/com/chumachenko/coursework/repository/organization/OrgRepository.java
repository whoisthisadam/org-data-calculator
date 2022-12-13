package com.chumachenko.coursework.repository.organization;

import com.chumachenko.coursework.domain.Organization;
import com.chumachenko.coursework.repository.CRUDRepository;

import java.util.List;

public interface OrgRepository extends CRUDRepository<Long, Organization>, AutoCloseable{

    List<Organization>findAllByUserId(Long userId);

    Long findNumberOfOrgsOfUser(Long userId);

    Organization create(Organization object);

    void deleteByUserIdAndName(Long userId, String name);

    Double updateLiquidity(Double liquidity, Long id);

    Double updateSolvency(Double solvency, Long id);

    Organization findByUserIdAndName(Long userId, String name);

    List<Organization>findTopSortedByLiquidity();

    List<Organization>findTopSortedBySolvency();
}
