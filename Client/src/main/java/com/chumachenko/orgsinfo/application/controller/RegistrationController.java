package com.chumachenko.orgsinfo.application.controller;


import com.chumachenko.orgsinfo.application.AlertManager;
import com.chumachenko.orgsinfo.application.ChangeScene;
import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;
import commands.fromserver.ResponseFromServer;
import entities.User;
import exception.RecurringEmailException;
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
import lombok.SneakyThrows;


import java.net.URL;
import java.util.ResourceBundle;


public class RegistrationController implements Initializable, Connectionable {

    private ClientConnection access;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    public Button loginButton;

    @FXML
    public void register(ActionEvent event){

        Window owner = submitButton.getScene().getWindow();

        String formError="Ошибка";

        if (firstNameField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }
        if (lastNameField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }

        if (emailField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        User user=new User(firstName,lastName,email,password);

        ResponseFromServer response;

        try {
            response=access.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(response==ResponseFromServer.SUCCESFULLY){
            ChangeScene.changeScene(event, "/fxml/congrats.fxml","Вход", access);
        }
        else if(response==ResponseFromServer.ERROR){
            AlertManager.showAlert(Alert.AlertType.ERROR, owner, "Ошибка",
                    "Пользователь с таким email уже зарегистрирован");
        }
    }

    @SneakyThrows
    @FXML
    public void login(ActionEvent event){
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login_form.fxml"));
        Parent root1 = fxmlLoader.load();
        LoginController loginController=fxmlLoader.getController();
        loginController.setAccess(access);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Log in");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setDefaultButton(false);
        loginButton.setOnAction(this::login);
        submitButton.setOnAction(this::register);
    }

    @Override
    public void setAccess(ClientConnection connection){access=connection;}
}
