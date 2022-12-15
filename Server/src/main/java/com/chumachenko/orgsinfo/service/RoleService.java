package com.chumachenko.orgsinfo.service;

import com.chumachenko.orgsinfo.repository.role.RoleRepoImpl;
import com.chumachenko.orgsinfo.repository.role.RoleRepository;
import entities.Role;

public class RoleService {

    RoleRepository roleRepository=new RoleRepoImpl();

    public Role findById(Long id){
        return roleRepository.findById(id);
    }

}
