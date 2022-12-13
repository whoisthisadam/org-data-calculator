package com.chumachenko.coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

public class CongratsController implements Initializable {
    @FXML
    public Button logInCongrats;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInCongrats.setOnAction(this::login);
    }

    @SneakyThrows
    @FXML
    public void login(ActionEvent event){
        Stage stage = (Stage) logInCongrats.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login_form.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Log in");
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
