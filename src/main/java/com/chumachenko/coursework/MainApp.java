package com.chumachenko.coursework;

import com.chumachenko.coursework.domain.orgdata.OrgData;
import com.chumachenko.coursework.exception.RecurringEmailException;
import com.chumachenko.coursework.exception.RecurringOrgNameException;
import com.chumachenko.coursework.repository.orgdata.OrgDataRepoImpl;
import com.chumachenko.coursework.repository.orgdata.OrgDataRepository;
import com.chumachenko.coursework.repository.user.UserRepoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
@RequiredArgsConstructor
public class MainApp extends Application{

//public class MainApp {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login_form.fxml")));
        stage.setTitle("Вход");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    public static void main(String[] args) throws RecurringEmailException, RecurringOrgNameException {
//        new UserRepoImpl().updatePassword(20L, "spririch04");
//    }

}
