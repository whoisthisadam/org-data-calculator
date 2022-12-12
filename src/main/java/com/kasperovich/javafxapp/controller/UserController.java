package com.kasperovich.javafxapp.controller;

import com.kasperovich.javafxapp.domain.Organization;
import com.kasperovich.javafxapp.domain.User;
import com.kasperovich.javafxapp.domain.enums.OrgType;
import com.kasperovich.javafxapp.exception.NoSuchEntityException;
import com.kasperovich.javafxapp.exception.RecurringOrgNameException;
import com.kasperovich.javafxapp.repository.organization.OrgRepoImpl;
import com.kasperovich.javafxapp.repository.organization.OrgRepository;
import com.kasperovich.javafxapp.repository.role.RoleRepoImpl;
import com.kasperovich.javafxapp.repository.role.RoleRepository;
import com.kasperovich.javafxapp.repository.user.UserRepoImpl;
import com.kasperovich.javafxapp.repository.user.UserRepository;
import com.kasperovich.javafxapp.util.ChangeScene;
import com.kasperovich.javafxapp.util.Options;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Window;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController implements Initializable {

    @FXML
    public Button logOutBtn;
    @FXML
    public AnchorPane profile;
    @FXML
    public Label userPreview;
    @FXML
    public Button showProfileBtn;
    @FXML
    public Button showMyOrgBtn;
    @FXML
    public Button top10Btn;
    @FXML
    public Label userNameAndSurname;
    @FXML
    public Label userEmail;
    @FXML
    public Label userRole;
    @FXML
    public Button editData;
    @FXML
    public TextField editEmail;
    @FXML
    public TextField editName;
    @FXML
    public Button saveChangesBtn;
    @FXML
    public Hyperlink changePasswordLink;
    @FXML
    public AnchorPane changePasswordPane;
    @FXML
    public TextField oldPasswordField;
    @FXML
    public TextField newPasswordField;
    @FXML
    public Label resOfChangePassword;
    @FXML
    public Button savePasswordBtn;
    @FXML
    public Button finishPasswordChange;
   @FXML
   public AnchorPane organizationsPane;
   @FXML
   public Button addOrgBtn;
   @FXML
   public Label noOrgInfo;
   @FXML
   public AnchorPane addOrgPane;
   @FXML
   public TextField orgTypeField;
   @FXML
   public TextField orgNameField;
   @FXML
   public TextField emplNumberField;
   @FXML
   public ListView<String> listOfOrg;
   @FXML
    public Button submitAdd;


    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profile.setVisible(false);
        editEmail.setVisible(false);
        editName.setVisible(false);
        saveChangesBtn.setVisible(false);
        changePasswordPane.setVisible(false);
        showProfileBtn.setOnAction(this::onProfileClicked);
        logOutBtn.setOnAction(event -> ChangeScene.changeScene(event,"/fxml/login_form.fxml","User Login"));
        editData.setOnAction(this::onEditClicked);
        changePasswordLink.setOnAction(this::changePassword);
        organizationsPane.setVisible(false);
        showMyOrgBtn.setOnAction(this::organizations);
        setContextMenu(listOfOrg);
    }

    public void initUser(User u){
        user=u;
        userPreview.setText(user.getEmail());
    }

    public void onProfileClicked(ActionEvent event){
        if(organizationsPane.isVisible())organizationsPane.setVisible(false);
        if(!profile.isVisible()){
            RoleRepository roleRepository=new RoleRepoImpl();
            userNameAndSurname.setText(user.getFirstName()+' '+user.getLastName());
            userEmail.setText(user.getEmail());
            userRole.setText(roleRepository.findById(user.getRoleId()).getName().toString().substring(5));
            profile.setVisible(true);
        }
        else {
            profile.setVisible(false);
        }
    }

    public void onEditClicked(ActionEvent event){
        editName.setVisible(true);
        editEmail.setVisible(true);
        editData.setVisible(false);
        saveChangesBtn.setVisible(true);
        editName.setText(userNameAndSurname.getText());
        editEmail.setText(userEmail.getText());
        saveChangesBtn.setOnAction(event1->{
                userNameAndSurname.setText(editName.getText());
                userEmail.setText(editEmail.getText());
                editName.setVisible(false);
                editEmail.setVisible(false);
                saveChangesBtn.setVisible(false);
                editData.setVisible(true);
                String nameAndSurname=editName.getText();
                StringBuilder name= new StringBuilder();
                for(int i=0;nameAndSurname.charAt(i)!=' ';i++){
                    name.append(nameAndSurname.charAt(i));
                }
                user.setFirstName(name.toString());
                user.setLastName(nameAndSurname.substring(nameAndSurname.indexOf(' ')+1));
                user.setEmail(editEmail.getText());
                UserRepository userRepository=new UserRepoImpl();
                userRepository.updateNamesAndEmail(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
            }
        );
    }

    public void changePassword(ActionEvent event){
        resOfChangePassword.setVisible(false);
        finishPasswordChange.setVisible(false);
        changePasswordPane.setVisible(true);
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        savePasswordBtn.setOnAction(event1 -> {
                Window window=oldPasswordField.getScene().getWindow();
                if(oldPasswordField.getText().isEmpty()){
                    Options.showAlert(Alert.AlertType.ERROR, window,"Error",
                            "Пожалуйста, введите старый пароль");
                    return;
                }
                if(newPasswordField.getText().isEmpty()){
                    Options.showAlert(Alert.AlertType.ERROR, window,"Error",
                            "Пожалуйста, введите новый пароль");
                    return;
                }
                if(encoder.matches(oldPasswordField.getText(), user.getPassword())){
                    user.setPassword(encoder.encode(newPasswordField.getText()));
                    UserRepository userRepository=new UserRepoImpl();
                    userRepository.updatePassword(user.getId(), user.getPassword());
                    resOfChangePassword.setText("Пароль успешно изменен");
                    resOfChangePassword.setVisible(true);
                    finishPasswordChange.setVisible(true);
                    finishPasswordChange.setText("Продолжить");
                    finishPasswordChange.setStyle("-fx-background-color: #90ee90");
                    finishPasswordChange.setOnAction(event2 -> changePasswordPane.setVisible(false));
                }
                else {
                    resOfChangePassword.setVisible(true);
                    resOfChangePassword.setText("Неверно введен старый пароль!");
                    finishPasswordChange.setVisible(true);
                    finishPasswordChange.setStyle("-fx-background-color: red");
                    finishPasswordChange.setText("Отмена");
                    finishPasswordChange.setOnAction(event2 -> changePasswordPane.setVisible(false));
                }
            }
        );
    }

    public void organizations(ActionEvent event){
        if(profile.isVisible())profile.setVisible(false);
        if(!organizationsPane.isVisible()) {
            organizationsPane.setVisible(true);
            addOrgPane.setVisible(false);
            try(OrgRepository orgRepository=new OrgRepoImpl()){
                setListOfOrg(orgRepository);
                ObservableList<String>orgListItems=listOfOrg.getItems();
                addOrgBtn.setOnAction(event1 -> addOrganization(event,orgRepository,orgListItems));
                noOrgInfo.setVisible(false);
            } catch (NoSuchEntityException e) {
                noOrgInfo.setVisible(true);
                ObservableList<String>observableList=FXCollections.observableList(new ArrayList<>());
                addOrgBtn.setOnAction(event1 -> addOrganization(event, new OrgRepoImpl(),observableList));
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        else {
            organizationsPane.setVisible(false);
        }
    }

    public void setListOfOrg(OrgRepository orgRepository){
        List<Integer>numbers=new ArrayList<>();
        for(int i=1;i<=orgRepository.findNumberOfOrgsOfUser(user.getId());i++){
            numbers.add(i);
        }
        List<Organization>orgList=orgRepository.findAllByUserId(user.getId());
        ObservableList<String>orgListItems=FXCollections.observableList(orgList
                .stream()
                .map(x->numbers.get(orgList.indexOf(x))+". "+x.getType().toString()+' '+x.getName())
                .collect(Collectors.toList()));
        listOfOrg.setItems(orgListItems);

    }

    public void addOrganization(ActionEvent event, OrgRepository orgRepository, ObservableList<String>observableList){
        addOrgPane.setVisible(true);
        submitAdd.setOnAction(event1 -> {
            Window window=oldPasswordField.getScene().getWindow();
            if (orgTypeField.getText().isEmpty()) {
                    Options.showAlert(Alert.AlertType.ERROR, window,"Error",
                            "Введите тип организации");
                    return;
                }
            if(orgNameField.getText().isEmpty()){
                Options.showAlert(Alert.AlertType.ERROR, window,"Error",
                        "Введите название организации");
                return;
                }
            Organization organization=new Organization();
            try {
                organization.setType(OrgType.valueOf(orgTypeField.getText()));
            }
            catch (IllegalArgumentException e){
                Options.showAlert(Alert.AlertType.ERROR, window, "Error",
                "Недопустимый тип организации");
                return;
            }
            organization.setName(orgNameField.getText());
            if(emplNumberField.getText().isEmpty()){
                organization.setNumberOfEmployees(null);
            }
            else {
                organization.setNumberOfEmployees(Integer.parseInt(emplNumberField.getText()));
            }
            organization.setUserId(user.getId());
            try{
                orgRepository.create(organization);
            }catch (RecurringOrgNameException e){
                Options.showAlert(Alert.AlertType.ERROR, window, "Ошибка",
                        "Вы уже добавляли организацию с таким юридическим именем");
                return;
            }

            addOrgPane.setVisible(false);
            String lastNumber=new String();
            if(!observableList.isEmpty()){
                lastNumber=observableList
                        .get(observableList.size()-1)
                        .substring(0,observableList.get(observableList.size()-1).indexOf("."));
            }
            else lastNumber="0";
            int number=Integer.parseInt(lastNumber)+1;
            observableList.add(number+". "+organization.getType()+' '+organization.getName());
            listOfOrg.setItems(observableList);
            noOrgInfo.setVisible(false);
            }
        );
    }

    public void deleteOrganization(ActionEvent event){

        StringBuilder idStr= new StringBuilder(new String());
        String selected=listOfOrg.getSelectionModel().getSelectedItem();
        StringBuilder name=new StringBuilder();
        int countBlank=0;
        for(int i=0;i<selected.length()-1;i++){ //считываем название организации из строки вывода
            if(selected.charAt(i)==' ')countBlank++;
            if(countBlank==2){
                name.append(selected.charAt(i+1));
            }
        }
        OrgRepository orgRepository=new OrgRepoImpl();
        orgRepository.deleteByUserIdAndName(user.getId(), name.toString());
        setListOfOrg(orgRepository);
    }

    public void setContextMenu(ListView listView){
        MenuItem menuItem=new MenuItem("Удалить");
        ContextMenu contextMenu=new ContextMenu(menuItem);
        listView.setContextMenu(contextMenu);
        menuItem.setOnAction(this::deleteOrganization);
    }
}
