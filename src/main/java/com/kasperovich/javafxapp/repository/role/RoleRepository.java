package com.kasperovich.javafxapp.repository.role;

import com.kasperovich.javafxapp.repository.CRUDRepository;
import com.kasperovich.javafxapp.domain.Role;


public interface RoleRepository extends CRUDRepository<Long, Role>, AutoCloseable {
}
