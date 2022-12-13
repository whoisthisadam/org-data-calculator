package com.chumachenko.coursework.repository.user;

import com.chumachenko.coursework.repository.CRUDRepository;
import com.chumachenko.coursework.domain.User;

public interface UserRepository extends CRUDRepository<Long, User>, AutoCloseable {


    User findByEmail(String email);

    User updatePassword(Long id, String password);

    User updateNamesAndEmail(Long id, String firstName, String lastName, String email);
}
