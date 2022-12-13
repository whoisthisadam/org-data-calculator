package com.chumachenko.coursework.controller;

import com.chumachenko.coursework.domain.User;
import com.chumachenko.coursework.exception.NoSuchEntityException;
import com.chumachenko.coursework.repository.organization.OrgRepository;
import com.chumachenko.coursework.repository.role.RoleRepoImpl;
import com.chumachenko.coursework.repository.role.RoleRepository;
import com.chumachenko.coursework.repository.user.UserRepository;
import com.chumachenko.coursework.repository.organization.OrgRepoImpl;
import com.chumachenko.coursework.repository.user.UserRepoImpl;
import com.chumachenko.coursework.util.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class AdminController implements Initializable {

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
        logoutBtn.setOnAction(event -> ChangeScene.changeScene(event,"/fxml/login_form.fxml", "User login"));
        showUsersBtn.setOnAction(this::onUsersCLicked);
        usersList.setOnMouseClicked(this::onUserClicked);
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
            List<User> users = new UserRepoImpl().findAll(null, 0);
            ObservableList<String> observableList = FXCollections.observableList(
                    new ArrayList<>(
                            users
                                    .stream()
                                    .map(x -> x.getId() + ". " + x.getEmail())
                                    .collect(Collectors.toList())
                    )
            );
            usersList.setItems(observableList);
//            usersList.setVisible(true);

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
        try(
                UserRepository userRepository=new UserRepoImpl();
                RoleRepository roleRepository=new RoleRepoImpl();

        ) {
            User user=userRepository.findById(id);
            userNameAndSurname.setText(user.getFirstName()+" "+user.getLastName());
            userEmail.setText(user.getEmail());
            userRole.setText(roleRepository.findById(user.getRoleId()).getName().toString().substring(5));
            userCreated.setText(user.getCreationDate().toString());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
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
        stage.setTitle("Menu");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public void setListOfOrgs(ActionEvent event){
        try (
                OrgRepository orgRepository=new OrgRepoImpl();
                UserRepository userRepository=new UserRepoImpl()
        )
        {
            List<String>list=orgRepository.findAll(null, 0).stream().map(
                    x->x.getId().toString()+". "+x.getType().toString()+' '+x.getName()+'('+userRepository.findById(x.getUserId()).getEmail()+')'
            ).collect(Collectors.toList());
            ObservableList<String>orgListItems=FXCollections.observableList(list);
            orgsList.setItems(orgListItems);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showOrgs(ActionEvent event){
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

