package com.chumachenko.orgsinfo.repository.user;


import com.chumachenko.orgsinfo.repository.CRUDRepository;
import entities.User;

public interface UserRepository extends CRUDRepository<Long, User>, AutoCloseable {


    User findByEmail(String email);

    User updatePassword(Long id, String password);

    User updateNamesAndEmail(Long id, String firstName, String lastName, String email);
}
