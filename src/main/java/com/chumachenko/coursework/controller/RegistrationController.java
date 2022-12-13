package com.chumachenko.coursework.controller;

import com.chumachenko.coursework.domain.User;
import com.chumachenko.coursework.exception.RecurringEmailException;
import com.chumachenko.coursework.repository.user.UserRepository;
import com.chumachenko.coursework.util.Options;
import com.chumachenko.coursework.repository.user.UserRepoImpl;
import com.chumachenko.coursework.util.ChangeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;


public class RegistrationController implements Initializable {

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
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }
        if (lastNameField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }

        if (emailField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Заполните все поля");
            return;
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        try(UserRepository userRepository=new UserRepoImpl()){
            userRepository.create(new User(firstName,lastName,email,password));
            ChangeScene.changeScene(event, "/fxml/congrats.fxml","Вход");
        }
        catch (RecurringEmailException e){
            Options.showAlert(Alert.AlertType.ERROR, owner, "Ошибка",
                    "Пользователь с таким именем уже зарегистрирован");
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    @FXML
    public void login(ActionEvent event){
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login_form.fxml"));
        Parent root1 = fxmlLoader.load();
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
}
