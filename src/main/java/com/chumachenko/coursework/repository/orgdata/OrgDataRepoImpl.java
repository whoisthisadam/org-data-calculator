package com.chumachenko.coursework.repository.orgdata;

import com.chumachenko.coursework.domain.OrgData;
import com.chumachenko.coursework.repository.organization.OrgRepository;
import com.chumachenko.coursework.util.DBPropertiesReader;
import com.chumachenko.coursework.util.Options;
import com.chumachenko.coursework.repository.organization.OrgRepoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrgDataRepoImpl implements OrgDataRepository {
    public OrgData orgDataRowMapping(ResultSet rs) throws SQLException {
        return OrgData.builder()
                .id(rs.getLong(OrgDataTableColumns.ID))
                .orgId(rs.getLong(OrgDataTableColumns.ORGANIZATION_ID))
                .bankroll(rs.getDouble(OrgDataTableColumns.BANKROLL))
                .shortInvestments(rs.getDouble(OrgDataTableColumns.SHORT_INVESTMENTS))
                .shortReceivables(rs.getDouble(OrgDataTableColumns.SHORT_RECEIVABLES))
                .shortLiabilities(rs.getDouble(OrgDataTableColumns.SHORT_LIABILITIES))
                .intangibleAssets(rs.getDouble(OrgDataTableColumns.INTANGIBLE_ASSETS))
                .mainAssets(rs.getDouble(OrgDataTableColumns.MAIN_ASSETS))
                .prodReverses(rs.getDouble(OrgDataTableColumns.PRODUCTION_REVERSES))
                .unfinishedProduction(rs.getDouble(OrgDataTableColumns.UNFINISHED_PRODUCTION))
                .finishedProducts(rs.getDouble(OrgDataTableColumns.FINISHED_PRODUCTS))
                .borrowedFunds(rs.getDouble(OrgDataTableColumns.BORROWED_FUNDS))
                .build();
    }

    @Override
    public OrgData findById(Long id) {
        return null;
    }

    @Override
    public Optional<OrgData> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OrgData> findAll() {
        return null;
    }

    @Override
    public List<OrgData> findAll(Optional<Integer> limit, int offset) {
        return null;
    }

    @Override
    public OrgData create(OrgData object){
        String insertQuery;

        Connection connection;
        PreparedStatement statement;

        try {


            if (object.getBankroll() == null) {
                insertQuery = "insert into orgsinfo.organization_data" +
                        "(organization_id, " +
                        "intangible_assets, main_assets, production_reverses, unfinished_production, finished_products, borrowed_funds)" +
                        " values (?, ?, ?, ?, ?, ?, ?);";

                connection = DBPropertiesReader.getConnection();
                statement = connection.prepareStatement(insertQuery);

                statement.setLong(1, object.getOrgId());
                statement.setDouble(2, object.getIntangibleAssets());
                statement.setDouble(3, object.getMainAssets());
                statement.setDouble(4, object.getProdReverses());
                statement.setDouble(5, object.getUnfinishedProduction());
                statement.setDouble(6, object.getFinishedProducts());
                statement.setDouble(7, object.getBorrowedFunds());

                statement.executeUpdate();

                ResultSet resultSet = connection.prepareStatement("SELECT currval('orgsinfo" +
                        ".organization_data_id_seq') as last_id").executeQuery();
                resultSet.next();
                long userLastInsertId = resultSet.getLong("last_id");

                return findById(userLastInsertId);
            } else if (object.getIntangibleAssets() == null) {
                insertQuery = "insert into orgsinfo.organization_data" +
                        "(organization_id, " +
                        "bankroll, short_investments, short_receivables, short_liabilities)" +
                        " values (?, ?, ?, ?, ?);";

                connection = DBPropertiesReader.getConnection();
                statement = connection.prepareStatement(insertQuery);

                statement.setLong(1, object.getOrgId());
                statement.setDouble(2, object.getBankroll());
                statement.setDouble(3, object.getShortInvestments());
                statement.setDouble(4, object.getShortReceivables());
                statement.setDouble(5, object.getShortLiabilities());

                statement.executeUpdate();

                OrgRepository orgRepository=new OrgRepoImpl();
                Double liquidity= Options.calculateLiquidity(
                        object.getBankroll(),
                        object.getShortInvestments(),
                        object.getShortReceivables(),
                        object.getShortLiabilities()
                );
                orgRepository.updateLiquidity(liquidity, object.getOrgId());
                ResultSet resultSet = connection.prepareStatement("SELECT currval('orgsinfo.organization_data_id_seq') as last_id").executeQuery();
                resultSet.next();
                long userLastInsertId = resultSet.getLong("last_id");

                return findById(userLastInsertId);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return object;
    }

    @Override
    public OrgData update(OrgData object) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Boolean isThisOrgPresent(Long orgId) {

        final String findNumberQuery="select count(organization_data)" +
                "from orgsinfo.organization_data" +
                " where organization_data.organization_id="+orgId;
        long result;
        Connection connection;
        Statement statement;
        ResultSet rs;

        try{
            connection=DBPropertiesReader.getConnection();
            statement= connection.createStatement();
            rs=statement.executeQuery(findNumberQuery);
            rs.next();
            result=rs.getLong("count");
            return result != 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public OrgData updateData(OrgData object) {
        String updateQuery;

        Connection connection;
        PreparedStatement statement;

        try {


            if (object.getBankroll() == null) {
                updateQuery = "update orgsinfo.organization_data" +
                        " set  intangible_assets="+object.getIntangibleAssets()+", main_assets="+object.getMainAssets()
                        +", production_reverses= "+object.getProdReverses()+", unfinished_production="+object.getUnfinishedProduction()
                        +", finished_products="+object.getFinishedProducts()+", borrowed_funds="+object.getBorrowedFunds()+
                        " where organization_id="+object.getOrgId();

                connection = DBPropertiesReader.getConnection();
                statement = connection.prepareStatement(updateQuery);

                statement.executeUpdate();

                OrgRepository orgRepository=new OrgRepoImpl();

                Double solvency=Options.calculateSolvency(
                        object.getIntangibleAssets(),
                        object.getMainAssets(),
                        object.getProdReverses(),
                        object.getUnfinishedProduction(),
                        object.getFinishedProducts(),
                        object.getBorrowedFunds()
                );
                orgRepository.updateSolvency(solvency, object.getOrgId());

                return object;
            } else if (object.getIntangibleAssets() == null) {
                updateQuery = "update orgsinfo.organization_data" +
                        " set  bankroll="+object.getBankroll()+", short_investments="+object.getShortInvestments()
                        +", short_receivables= "+object.getShortReceivables()+", short_liabilities="+object.getShortLiabilities()+
                        " where organization_id="+object.getOrgId();

                connection = DBPropertiesReader.getConnection();
                statement = connection.prepareStatement(updateQuery);

                statement.executeUpdate();

                OrgRepository orgRepository=new OrgRepoImpl();
                Double liquidity=Options.calculateLiquidity(
                        object.getBankroll(),
                        object.getShortInvestments(),
                        object.getShortReceivables(),
                        object.getShortLiabilities()
                );
                orgRepository.updateLiquidity(liquidity, object.getOrgId());

                return object;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return object;
    }

}
