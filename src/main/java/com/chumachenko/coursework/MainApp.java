package com.chumachenko.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
@RequiredArgsConstructor
public class MainApp extends Application{
//public class MainApp{
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login_form.fxml")));
        stage.setTitle("Log in");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    public static void main(String[] args) throws RecurringEmailException, RecurringOrgNameException {
//        OrgDataRepository orgDataRepository=new OrgDataRepoImpl();
//        OrgData orgData=new OrgData();
//        orgData.setOrgId(4L);
//        orgData.setIntangibleAssets(0.45);
//        orgData.setMainAssets(0.3);
//        orgData.setProdReverses(1.23);
//        orgData.setUnfinishedProduction(2.34);
//        orgData.setFinishedProducts(1.2);
//        orgData.setBorrowedFunds(3.0);
//        orgDataRepository.create(orgData);
//    }

}
