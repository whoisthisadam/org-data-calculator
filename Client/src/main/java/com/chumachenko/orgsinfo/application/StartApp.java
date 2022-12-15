package com.chumachenko.orgsinfo.application;

import com.chumachenko.orgsinfo.application.controller.LoginController;
//import com.chumachenko.orgsinfo.application.controller.TestController;
import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class StartApp extends Application {



    public static Properties getPropertiesFromConfig() throws IOException {

        var properties = new Properties();
        String propFileName = "Client/src/main/resources/config.properties";
        var inputStream = new FileInputStream(propFileName);
        if (inputStream == null)
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        properties.load(inputStream);
        return properties;
    }
    @Override
    public void start(Stage stage) throws Exception {
        var properties = getPropertiesFromConfig();



        ClientConnection clientConnectionModule = new ClientConnection(
                properties.getProperty("serverIp"),
                Integer.parseInt(properties.getProperty("serverPort")));

        var state = clientConnectionModule.connectToServer();
        if (!state){
            AlertManager.showWarningAlert("Cannot connect to server", "");
            return;
        }


        System.out.println(getClass());
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/login_form.fxml")));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setAccess(clientConnectionModule);

        stage.setTitle("Вход");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
