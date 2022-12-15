package com.chumachenko.orgsinfo.application.controller;

import com.chumachenko.orgsinfo.application.ChangeScene;
import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class AdminController implements Initializable, Connectionable {

    ClientConnection access;

    @Override
    public void setAccess(ClientConnection access) {
        this.access=access;
    }

    @FXML
    public AnchorPane userDataPane;
    @FXML
    public Label userNameAndSurname;
    @FXML
    public Label userEmail;
    @FXML
    public Label userRole;
    @FXML
    public Label userCreated;
    @FXML
    public Button backToListOfUsers;
    @FXML
    public Button adminToUsersBtn;
    @FXML
    public AnchorPane showOrgsPane;
    @FXML
    public ListView<String> orgsList;
    @FXML
    public Button showOrgsBtn;
    @FXML
    public AnchorPane usersPane;
    @FXML
    private Button showUsersBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private ListView<String> usersList;

    User admin=new User();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDataPane.setVisible(false);
        logoutBtn.setOnAction(event -> ChangeScene.changeScene(event,"/fxml/login_form.fxml", "User login", access));
        showUsersBtn.setOnAction(this::onUsersCLicked);
        usersList.setOnMouseClicked(this::onUserClicked);
        usersPane.setVisible(false);
        adminToUsersBtn.setOnAction(this::switchToUser);
        showOrgsPane.setVisible(false);
        showOrgsBtn.setOnAction(this::showOrgs);
    }

    public void initAdmin(User user){
        admin=user;
    }

    @FXML
    public void onUsersCLicked(ActionEvent event) {
        if (showOrgsPane.isVisible()) showOrgsPane.setVisible(false);
        if (!usersPane.isVisible()) {
            usersPane.setVisible(true);
            ObservableList<String> observableList = null;
            try {
                observableList = FXCollections.observableList(
                        access.getListOfUsersString()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            usersList.setItems(observableList);

        } else {
            usersPane.setVisible(false);
        }
    }

    public void onUserClicked(MouseEvent event){
        userDataPane.setVisible(true);
        StringBuilder idStr= new StringBuilder(new String());
        String selected=usersList.getSelectionModel().getSelectedItem();
        for(int i=0;i<selected.indexOf(".");i++){
            idStr.append(selected.charAt(i));
        }
        Long id=Long.parseLong(idStr.toString());
        User user= null;
        try {
            user = access.getUserById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        userNameAndSurname.setText(user.getFirstName()+" "+user.getLastName());
        userEmail.setText(user.getEmail());
        if(user.getRoleId()==1L)userRole.setText("USER");
        else if(user.getRoleId()==2L)userRole.setText("ADMIN");
        userCreated.setText(user.getCreationDate().toString());
        backToListOfUsers.setOnAction(event1 -> {
            userDataPane.setVisible(false);
            usersList.setVisible(true);
        });
    }

    public void switchToUser(ActionEvent event){
        Stage stage=(Stage) adminToUsersBtn.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/user_menu.fxml")));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserController userController=loader.getController();
        userController.initUser(admin);
        userController.setAccess(access);
        stage.setTitle("Menu");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public void setListOfOrgs(ActionEvent event) {
        List<String>list= null;
        try {
            list = access.getListOfOrgsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObservableList<String>orgListItems=FXCollections.observableList(list);
        orgsList.setItems(orgListItems);
    }

    public void showOrgs(ActionEvent event) {
        if(usersPane.isVisible())usersPane.setVisible(false);
        if (userDataPane.isVisible()) userDataPane.setVisible(false);
        if(!showOrgsPane.isVisible()){
            showOrgsPane.setVisible(true);
            setListOfOrgs(event);
            orgsList.setVisible(true);
        }
        else{
            showOrgsPane.setVisible(false);
        }
    }
}

