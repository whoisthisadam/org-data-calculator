package com.kasperovich.javafxapp.repository.role;


import com.kasperovich.javafxapp.domain.Role;
import com.kasperovich.javafxapp.domain.enums.RolesType;
import com.kasperovich.javafxapp.exception.NoSuchEntityException;
import com.kasperovich.javafxapp.util.DBPropertiesReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class RoleRepoImpl implements RoleRepository {

    private Role roleRowMapping(ResultSet rs) throws SQLException {

        return Role.builder()
                .id(rs.getLong(RoleTableColumns.ID))
                .name(RolesType.valueOf(rs.getString(RoleTableColumns.NAME)))
                .build();
    }
    @Override
    public Role findById(Long id) {
        final String findByIdQuery = "select * from testjfx.roles where id = " + id;

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            connection = DBPropertiesReader.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findByIdQuery);
            boolean hasRow = rs.next();
            if (hasRow) {
                return roleRowMapping(rs);
            } else {
                throw new NoSuchEntityException("Entity User with id " + id + " does not exist", 404);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Optional<Role> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public List<Role> findAll(Optional<Integer> limit, int offset) {
        return null;
    }

    @Override
    public Role create(Role object) {
        return null;
    }

    @Override
    public Role update(Role object) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
