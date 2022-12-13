package com.kasperovich.javafxapp.repository.user;

import com.kasperovich.javafxapp.domain.User;
import com.kasperovich.javafxapp.repository.CRUDRepository;

public interface UserRepository extends CRUDRepository<Long, User>, AutoCloseable {


    User findByEmail(String email);

    User updatePassword(Long id, String password);

    User updateNamesAndEmail(Long id, String firstName, String lastName, String email);
}
