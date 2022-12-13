package com.chumachenko.coursework.repository.formules;

import com.chumachenko.coursework.domain.Formula;
import com.chumachenko.coursework.util.DBPropertiesReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FormulesRepoImpl implements FormulesRepository{

    public Formula formulaRowMapping(ResultSet rs) throws SQLException {
        return Formula
                .builder()
                .id(rs.getLong("id"))
                .value(rs.getString("value"))
                .build();
    }

    @Override
    public Formula findById(Long id) {
        return null;
    }

    @Override
    public Optional<Formula> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Formula> findAll() {
        final  String findAllQuery="select * from orgsinfo.formules";

        Connection connection;
        Statement statement;
        ResultSet rs;

        List<Formula>result=new ArrayList<>();
        try{
            connection= DBPropertiesReader.getConnection();
            statement=connection.createStatement();
            rs=statement.executeQuery(findAllQuery);
            while (rs.next()){
                result.add(formulaRowMapping(rs));
            }
            return result;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.err.println("SQL Issues!");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Formula> findAll(Optional<Integer> limit, int offset) {
        return null;
    }

    @Override
    public Formula create(Formula object) {
        return null;
    }

    @Override
    public Formula update(Formula object) {
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
