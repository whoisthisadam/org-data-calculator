package com.chumachenko.orgsinfo.serverinfo;

import com.chumachenko.orgsinfo.repository.user.UserRepoImpl;
import com.chumachenko.orgsinfo.repository.user.UserRepository;
import com.chumachenko.orgsinfo.service.*;
import commands.fromserver.ResponseFromServer;
import commands.toserver.Command;
import entities.OrgData;
import entities.Organization;
import entities.Role;
import entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ClientProcessingThread extends Thread{


    private final ConnectedClientInfo clientInfo;

    private final ObjectOutputStream objectOutputStream;

    private final ObjectInputStream objectInputStream;

    public ClientProcessingThread(ConnectedClientInfo clientInfo) throws IOException {
        this.clientInfo = clientInfo;
        var socket = clientInfo.getConnectionSocket();
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void sendObject(Serializable object) throws IOException {

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    private <T> T receiveObject() throws IOException, ClassNotFoundException {

        return (T) objectInputStream.readObject();
    }

    @Override
    public void run() {

        while (true) {
            try {
                startClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void interrupt() {
        try {
            clientInfo.getConnectionSocket().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.interrupt();
    }

    public ConnectedClientInfo getClientInfo() {
        return clientInfo;
    }

    UserService userService=new UserService();

    RoleService roleService=new RoleService();

    OrgService orgService=new OrgService();

    OrgDataService orgDataService=new OrgDataService();

    FormulaService formulaService=new FormulaService();


    private void startClient() throws Exception{



        Command command=receiveObject();
        switch (command){
            case FIND_USER_BY_EMAIL:{
                String email=receiveObject();
                UserRepository userRepository=new UserRepoImpl();
                User user=userRepository.findByEmail(email);
                sendObject(user);
                break;
            }
            case CHECK_IF_USER_EXIST:{
                isUserExists();
                break;
            }

            case REGISTER:{
                registerUser();
                break;
            }
            case GET_LIST_OF_STRING_USERS:{
                sendObject((Serializable) userService.getListOfStringUsers());
                break;
            }
            case GET_LIST_OF_STRING_ORGS:{
                sendObject((Serializable) orgService.getListOfOrgsString());
                break;
            }
            case FIND_USER_BY_ID:{
                findUserById();
                break;
            }
            case FIND_ROLE_BY_ID:{
                findRoleById();
                break;
            }
            case UPDATE_NAME_AND_EMAIL:{
                updateNameAndEmail();
                break;
            }
            case UPDATE_PASSWORD:{
                updatePassword();
                break;
            }
            case FIND_NUMBER_OF_USER_ORGS:{
                getNumberOfUserOrgs();
                break;
            }
            case CREATE_ORGANIZATION:{
                Organization organization=receiveObject();
                ResponseFromServer response=orgService.createOrganization(organization);
                sendObject(response);
                break;
            }
            case FIND_ALL_ORGS_BY_USER_ID:{
                findAllOrgsByUserId();
                break;
            }
            case DELETE_ORGANIZATION:{
                deleteOrganization();
                break;
            }
            case FIND_ORG_BY_USER_ID_AND_NAME:{
                findOrgByUserIdAndName();
                break;
            }
            case CHECK_IF_THIS_ORG_PRESENT:{
                checkIfOrgDataPresent();
                break;
            }

            case CREATE_ORG_DATA:{
                createOrgData();
                break;
            }

            case UPDATE_ORG_DATA:{
                updateOrgData();
                break;
            }

            case GET_TOP_SOLVENCY:{
                sendObject((Serializable) orgService.findTopSortedBySolvency());
                break;
            }

            case GET_TOP_LIQUIDITY:{
                sendObject((Serializable) orgService.findTopSortedByLiquidity());
                break;
            }

            case GET_AVERAGE_SOLVENCY:{
                sendObject(orgService.calcAvgSolvency());
                break;
            }

            case GET_AVERAGE_LIQUIDITY:{
                sendObject(orgService.calcAvgLiquidity());
                break;
            }

            case GET_ALL_FORMULAS:{
                sendObject((Serializable) formulaService.findAll());
                break;
            }

            default:{
                sendObject(ResponseFromServer.UNKOWN_COMMAND);
            }
        }
    }


    private void isUserExists() throws IOException, ClassNotFoundException {
        String email=receiveObject();
        boolean result=userService.isUserExists(email);
        sendObject(result);
    }

    public void registerUser() throws IOException, ClassNotFoundException {
        User user=receiveObject();
        ResponseFromServer response=userService.registerUser(user);
        sendObject(response);
    }

    public void findUserById() throws IOException, ClassNotFoundException {
        Long id=receiveObject();
        User user=userService.findById(id);
        sendObject(user);
    }

    public void findRoleById() throws IOException, ClassNotFoundException {
        Long id=receiveObject();
        Role role=roleService.findById(id);
        sendObject(role);
    }

    public void updateNameAndEmail() throws IOException, ClassNotFoundException {
        Long id=receiveObject();
        String name=receiveObject();
        String surname=receiveObject();
        String email=receiveObject();
        ResponseFromServer response=userService.updateNameAndEmail(id, name, surname, email);
        sendObject(response);
    }

    public void updatePassword() throws IOException, ClassNotFoundException {
        Long id=receiveObject();
        String password=receiveObject();
        ResponseFromServer response=userService.updatePassword(id,password);
        sendObject(response);
    }

    public void getNumberOfUserOrgs() throws IOException, ClassNotFoundException {
        Long userId=receiveObject();
        Long number= orgService.findNumberOfOrgsOfUser(userId);
        sendObject(number);
    }

    public void findAllOrgsByUserId() throws IOException, ClassNotFoundException {
        Long userId=receiveObject();
        sendObject((Serializable) orgService.findAllOrgsByUserId(userId));
    }

    public void deleteOrganization() throws IOException, ClassNotFoundException {
        Long userId=receiveObject();
        String name=receiveObject();
        sendObject(orgService.deleteOrganization(userId,name));
    }

    public void findOrgByUserIdAndName() throws IOException, ClassNotFoundException {
        Long userId=receiveObject();
        String name=receiveObject();
        sendObject(orgService.findOrgByUserIdAndName(userId, name));
    }

    public void checkIfOrgDataPresent() throws IOException, ClassNotFoundException {
        Long orgId=receiveObject();
        sendObject(orgDataService.isThisOrgPresent(orgId));
    }

    public void updateOrgData() throws IOException, ClassNotFoundException {
        OrgData orgData=receiveObject();
        sendObject(orgDataService.updateOrgData(orgData));
    }

    public void createOrgData() throws IOException, ClassNotFoundException {
        OrgData orgData=receiveObject();
        sendObject(orgDataService.createOrgData(orgData));
    }

}
