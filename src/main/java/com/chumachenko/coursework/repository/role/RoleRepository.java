package com.chumachenko.coursework.repository.role;

import com.chumachenko.coursework.domain.Role;
import com.chumachenko.coursework.repository.CRUDRepository;


public interface RoleRepository extends CRUDRepository<Long, Role>, AutoCloseable {
}
