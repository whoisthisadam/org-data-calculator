package com.kasperovich.javafxapp.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Options {


    public static Alert showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        return alert;
    }
}
