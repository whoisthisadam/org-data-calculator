package com.chumachenko.orgsinfo.connection.clientconnection;


import commands.fromserver.ResponseFromServer;
import commands.toserver.Command;
import entities.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public class ClientConnection {

    private Socket connectionSocket;
    private final String serverIp;
    private final int serverPort;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientConnection(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public boolean connectToServer() throws IOException {

        connectionSocket = new Socket(serverIp, serverPort);
        connectionSocket.setSoTimeout(3000);
        if (!connectionSocket.isConnected()) return false;
        objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
        return true;
    }

    private void sendObject(Serializable object) throws IOException {

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    private <T> T receiveObject() throws Exception {

        return (T) objectInputStream.readObject();
    }


    public User findUserByEmail(String email) throws Exception {
        sendObject(Command.FIND_USER_BY_EMAIL);
        sendObject(email);
        return receiveObject();
    }

    public boolean checkIfUserExist(String email) throws Exception {
        sendObject(Command.CHECK_IF_USER_EXIST);
        sendObject(email);
        return receiveObject();
    }


    public ResponseFromServer register(User user) throws Exception {
        sendObject(Command.REGISTER);
        sendObject(user);
        ResponseFromServer responseFromServer=receiveObject();
        return responseFromServer;
    }

    public List<String> getListOfUsersString() throws Exception {
        sendObject(Command.GET_LIST_OF_STRING_USERS);
        return receiveObject();
    }

    public List<String> getListOfOrgsString() throws Exception {
        sendObject(Command.GET_LIST_OF_STRING_ORGS);
        return  receiveObject();
    }

    public User getUserById(Long id) throws Exception {
        sendObject(Command.FIND_USER_BY_ID);
        sendObject(id);
        return receiveObject();
    }

    public Role getRoleById(Long id) throws Exception {
        sendObject(Command.FIND_ROLE_BY_ID);
        sendObject(id);
        return receiveObject();
    }

    public ResponseFromServer updateNameAndEmail
            (Long id, String name, String surname, String email)
            throws Exception
    {
        sendObject(Command.UPDATE_NAME_AND_EMAIL);
        sendObject(id);
        sendObject(name);
        sendObject(surname);
        sendObject(email);
        return receiveObject();
    }

    public ResponseFromServer updatePassword
            (Long id, String password)
            throws Exception
    {
        sendObject(Command.UPDATE_PASSWORD);
        sendObject(id);
        sendObject(password);
        return receiveObject();
    }

    public ResponseFromServer createOrganization(Organization organization) throws Exception {
        sendObject(Command.CREATE_ORGANIZATION);
        sendObject(organization);
        return receiveObject();
    }

    public Long getNumberOfOrgsUser(Long id) throws Exception {
        sendObject(Command.FIND_NUMBER_OF_USER_ORGS);
        sendObject(id);
        return receiveObject();
    }

    public List<Organization>findAllOrgsByUserId(Long id)throws Exception{
        sendObject(Command.FIND_ALL_ORGS_BY_USER_ID);
        sendObject(id);
        return receiveObject();
    }

    public ResponseFromServer deleteOrganization(Long id, String name)throws Exception{
        sendObject(Command.DELETE_ORGANIZATION);
        sendObject(id);
        sendObject(name);
        return receiveObject();
    }

    public Organization findOrgByUserIdAndName(Long userId, String name) throws Exception{
        sendObject(Command.FIND_ORG_BY_USER_ID_AND_NAME);
        sendObject(userId);
        sendObject(name);
        return receiveObject();
    }

    public boolean isThisOrgPresent(Long orgId) throws Exception {
        sendObject(Command.CHECK_IF_THIS_ORG_PRESENT);
        sendObject(orgId);
        return receiveObject();
    }

    public OrgData updateOrgData(OrgData orgData) throws Exception {
        sendObject(Command.UPDATE_ORG_DATA);
        sendObject(orgData);
        return receiveObject();
    }

    public OrgData createOrgData(OrgData orgData) throws Exception {
        sendObject(Command.CREATE_ORG_DATA);
        sendObject(orgData);
        return receiveObject();
    }

    public List<Organization>findTopSortedByLiquidity() throws Exception {
        sendObject(Command.GET_TOP_LIQUIDITY);
        return receiveObject();
    }

    public List<Organization>findTopSortedBySolvency() throws Exception {
        sendObject(Command.GET_TOP_SOLVENCY);
        return receiveObject();
    }

    public Double getAvgLiquidity()throws Exception{
        sendObject(Command.GET_AVERAGE_LIQUIDITY);
        return receiveObject();
    }

    public Double getAvgSolvency()throws Exception{
        sendObject(Command.GET_AVERAGE_SOLVENCY);
        return receiveObject();
    }

    public List<Formula> getAllFormulas()throws Exception{
        sendObject(Command.GET_ALL_FORMULAS);
        return receiveObject();
    }

    public ResponseFromServer checkIfThisUserHasThisOrg(Long userid, String orgName) throws Exception {
        sendObject(Command.CHECK_IF_USER_HAS_THIS_ORG);
        sendObject(orgName);
        sendObject(userid);
        return receiveObject();
    }

    public ResponseFromServer updateOrgName(String name, Long id) throws  Exception{
        sendObject(Command.CHANGE_ORG_NAME);
        sendObject(name);
        sendObject(id);
        return receiveObject();
    }

}
