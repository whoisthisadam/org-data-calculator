//package com.chumachenko.orgsinfo.application.controller;
//
//import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class TestController implements Initializable {
//
//    private ClientConnection access;
//
////    private ClientConnection connection;
//    @FXML
//    private Label helloLabel;
//
////
////    public String getNameFromServer(ActionEvent event){
////
////    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        helloLabel.setOnMouseClicked(event -> {
//            try {
//                helloLabel.setText(access.sendStringToServer(helloLabel.getText()));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//    public void setAccess(ClientConnection connection) {
//        access = connection;
//    }
//}
