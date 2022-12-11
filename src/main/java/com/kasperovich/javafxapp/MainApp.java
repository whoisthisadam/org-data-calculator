package com.kasperovich.javafxapp;

import com.kasperovich.javafxapp.repository.organization.OrgRepoImpl;
import com.kasperovich.javafxapp.repository.user.UserRepoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
@RequiredArgsConstructor
public class MainApp {


//    @Override
//    public void start(Stage stage) throws Exception {
//        System.out.println(getClass());
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login_form.fxml")));
//        stage.setTitle("Log in");
//        stage.setScene(new Scene(root, 800, 500));
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }

    public static void main(String[] args) {
        System.out.println(new OrgRepoImpl().findNumberOfOrgsOfUser(15L));
    }

}
