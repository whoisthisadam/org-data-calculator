package com.chumachenko.orgsinfo.repository.role;


import com.chumachenko.orgsinfo.repository.CRUDRepository;
import entities.Role;


public interface RoleRepository extends CRUDRepository<Long, Role>, AutoCloseable {
}
