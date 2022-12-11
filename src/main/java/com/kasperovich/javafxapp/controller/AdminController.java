package com.kasperovich.javafxapp.controller;

import com.kasperovich.javafxapp.domain.User;
import com.kasperovich.javafxapp.exception.NoSuchEntityException;
import com.kasperovich.javafxapp.repository.role.RoleRepoImpl;
import com.kasperovich.javafxapp.repository.role.RoleRepository;
import com.kasperovich.javafxapp.repository.user.UserRepoImpl;
import com.kasperovich.javafxapp.repository.user.UserRepository;
import com.kasperovich.javafxapp.util.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class AdminController implements Initializable {

    @FXML
    public AnchorPane userData;
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
    private Button showUsersBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private ListView<String> usersList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData.setVisible(false);
        usersList.setVisible(false);
        logoutBtn.setOnAction(event -> ChangeScene.changeScene(event,"/fxml/login_form.fxml", "User login"));
        showUsersBtn.setOnAction(this::onUsersCLicked);
        usersList.setOnMouseClicked(this::onUserClicked);
    }

    @FXML
    public void onUsersCLicked(ActionEvent event){
        if(!usersList.isVisible()){
            List<User>users=new UserRepoImpl().findAll(null,0);
            ObservableList<String>observableList= FXCollections.observableList(
                    new ArrayList<>(
                            users
                            .stream()
                            .map(x-> x.getId() + ". " + x.getEmail())
                            .collect(Collectors.toList())
                    )
            );
            usersList.setItems(observableList);
            usersList.setVisible(true);
        }
        else {
            usersList.setVisible(false);
        }
    }

    public void onUserClicked(MouseEvent event){
        usersList.setVisible(false);
        userData.setVisible(true);
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
        backToListOfUsers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userData.setVisible(false);
                usersList.setVisible(true);
            }
        });

    }
}

