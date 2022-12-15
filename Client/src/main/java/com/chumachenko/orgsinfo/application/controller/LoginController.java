package com.chumachenko.orgsinfo.application.controller;

import com.chumachenko.orgsinfo.application.AlertManager;
import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;
import entities.User;
import exception.NoSuchEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable, Connectionable {

    private ClientConnection access;


    @FXML
    public Button defUserBtn;

    @FXML
    public Button defAdminBtn;

    @FXML
    TextField emailField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button submitButton;

    @FXML
    Button signUpButton;

    @FXML
    public void login(ActionEvent actionEvent) {

        Window owner = submitButton.getScene().getWindow();

        String formError="Ошибка";

        if (emailField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Введите адрес эл. почты");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Введите пароль");
            return;
        }


        String email= emailField.getText();

        PasswordEncoder encoder=new BCryptPasswordEncoder();

        boolean isUserExist= false;
        try {
            isUserExist = access.checkIfUserExist(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!isUserExist){
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, "Ошибка",
                    "Пользователя с таким именем не существует");
            return;
        }

        User user= null;
        try {
            user = access.findUserByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!encoder.matches(passwordField.getText(),user.getPassword())){
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, "Неверный пароль",
                    "Неверный пароль");
            return;
        }
        Stage stage=(Stage) signUpButton.getScene().getWindow();
        stage.close();
        switch ((int)(long)user.getRoleId()){
            case 1:{
                FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/user_menu.fxml")));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                UserController userController=loader.getController();
                userController.initUser(user);
                userController.setAccess(access);
                stage.setTitle("Menu");
                stage.setScene(new Scene(root, 800, 500));
                stage.show();
                break;
            }
            case 2:{
                FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/admin_menu.fxml")));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                AdminController adminController=loader.getController();
                adminController.initAdmin(user);
                adminController.setAccess(access);
                stage.setTitle("Menu(ADMIN)");
                stage.setScene(new Scene(root, 800, 500));
                stage.show();
                break;
            }
            default:throw new RuntimeException();
        }
    }

    @FXML
    public void signUp(ActionEvent event) throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/registration_form.fxml"));
        Parent root1 = fxmlLoader.load();
        RegistrationController registrationController=fxmlLoader.getController();
        registrationController.setAccess(access);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Sign up");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setDefaultButton(true);
        signUpButton.setFocusTraversable(false);
        submitButton.setOnAction(this::login);
        defUserBtn.setOnAction(event -> {
            emailField.setText("irinaigor@gmail.com");
            passwordField.setText("irina1");
        });
        defAdminBtn.setOnAction(event -> {
            emailField.setText("adamkasper24@gmail.com");
            passwordField.setText("16242003Ak");
        });
    }



    @Override
    public void setAccess(ClientConnection singUpAccess) {
        access = singUpAccess;
    }


}
