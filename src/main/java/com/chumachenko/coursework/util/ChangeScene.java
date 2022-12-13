package com.chumachenko.coursework.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ChangeScene {

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        FXMLLoader loader = new FXMLLoader(ChangeScene.class.getResource(fxmlFile));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(Objects.requireNonNull(root)));
        stage.show();
    }
}
