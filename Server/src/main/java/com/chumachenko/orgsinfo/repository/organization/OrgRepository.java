package com.chumachenko.orgsinfo.repository.organization;


import com.chumachenko.orgsinfo.repository.CRUDRepository;
import entities.Organization;

import java.util.List;
import java.util.Optional;

public interface OrgRepository extends CRUDRepository<Long, Organization>, AutoCloseable{

    List<Organization>findAllByUserId(Long userId);

    Long findNumberOfOrgsOfUser(Long userId);

    Organization create(Organization object);

    void deleteByUserIdAndName(Long userId, String name);

    Double updateLiquidity(Double liquidity, Long id);

    Double updateSolvency(Double solvency, Long id);

    Optional<Organization> findByUserIdAndName(Long userId, String name);

    List<Organization>findTopSortedByLiquidity();

    List<Organization>findTopSortedBySolvency();

    Double calculateAverageLiquidity();

    Double calculateAverageSolvency();
}
