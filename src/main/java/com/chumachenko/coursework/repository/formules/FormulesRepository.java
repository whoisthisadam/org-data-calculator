package com.chumachenko.coursework.repository.formules;

import com.chumachenko.coursework.domain.Formula;
import com.chumachenko.coursework.repository.CRUDRepository;

public interface FormulesRepository extends CRUDRepository<Long, Formula>, AutoCloseable {
}
