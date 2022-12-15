package com.chumachenko.orgsinfo.repository.formules;


import com.chumachenko.orgsinfo.repository.CRUDRepository;
import entities.Formula;

public interface FormulesRepository extends CRUDRepository<Long, Formula>, AutoCloseable {
}
