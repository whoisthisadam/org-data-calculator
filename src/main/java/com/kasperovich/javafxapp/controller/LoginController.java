package com.kasperovich.javafxapp.controller;

import com.kasperovich.javafxapp.domain.User;
import com.kasperovich.javafxapp.exception.NoSuchEntityException;
import com.kasperovich.javafxapp.repository.user.UserRepoImpl;
import com.kasperovich.javafxapp.repository.user.UserRepository;
import com.kasperovich.javafxapp.util.Options;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
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

        String formError="Form Error!";

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


        String email= emailField.getText();

        PasswordEncoder encoder=new BCryptPasswordEncoder();


        try(UserRepository userRepository=new UserRepoImpl()){
            User user=userRepository.findByEmail(email);
            if(!encoder.matches(passwordField.getText(),user.getPassword())){
                Options.showAlert(Alert.AlertType.ERROR, owner, "Incorrect password",
                        "Password is incorrect\nTry again.");
                return;
            }
            Stage stage=(Stage) signUpButton.getScene().getWindow();
            stage.close();
            switch ((int)(long)user.getRoleId()){
                case 1:{
                    FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/user_menu.fxml")));
                    Parent root = loader.load();
                    UserController userController=loader.getController();
                    userController.initUser(user);
                    stage.setTitle("Menu");
                    stage.setScene(new Scene(root, 800, 500));
                    stage.show();
                    break;
                }
                case 2:{
                    FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/admin_menu.fxml")));
                    Parent root = loader.load();
                    AdminController adminController=loader.getController();
                    adminController.initAdmin(user);
                    stage.setTitle("Menu(ADMIN)");
                    stage.setScene(new Scene(root, 800, 500));
                    stage.show();
                    break;
                }
                default:throw new RuntimeException();
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            Options.showAlert(Alert.AlertType.ERROR, owner, "No user",
                    "User with this email does not exist\nTry again");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void signUp(ActionEvent event) throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/registration_form.fxml"));
        Parent root1 = fxmlLoader.load();
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
            emailField.setText("ivanspr@gmail.com");
            passwordField.setText("spirich04");
        });
        defAdminBtn.setOnAction(event -> {
            emailField.setText("adamkasper24@gmail.com");
            passwordField.setText("16242003Ak");
        });
    }
}
