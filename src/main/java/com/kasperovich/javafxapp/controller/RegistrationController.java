package com.kasperovich.javafxapp.controller;

import com.kasperovich.javafxapp.domain.User;
import com.kasperovich.javafxapp.exception.RecurringEmailException;
import com.kasperovich.javafxapp.repository.user.UserRepoImpl;
import com.kasperovich.javafxapp.repository.user.UserRepository;
import com.kasperovich.javafxapp.util.ChangeScene;
import com.kasperovich.javafxapp.util.Options;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

        String formError="Form Error!";

        if (firstNameField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Please enter your first name");
            return;
        }
        if (lastNameField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Please enter your last name");
            return;
        }

        if (emailField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Please enter your email");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            Options.showAlert(Alert.AlertType.ERROR, owner, formError,
                    "Please enter a password");
            return;
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        try(UserRepository userRepository=new UserRepoImpl()){
            userRepository.create(new User(firstName,lastName,email,password));
            ChangeScene.changeScene(event, "/fxml/congrats.fxml","User login");
        }
        catch (RecurringEmailException e){
            Options.showAlert(Alert.AlertType.ERROR, owner, "Sign up error",
                    "Sorry, user with this email already registered");
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
