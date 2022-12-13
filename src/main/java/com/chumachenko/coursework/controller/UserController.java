package com.chumachenko.coursework.controller;

import com.chumachenko.coursework.domain.Organization;
import com.chumachenko.coursework.repository.organization.OrgRepository;
import com.chumachenko.coursework.repository.role.RoleRepoImpl;
import com.chumachenko.coursework.domain.User;
import com.chumachenko.coursework.domain.enums.OrgType;
import com.chumachenko.coursework.domain.orgdata.OrgData;
import com.chumachenko.coursework.exception.NoSuchEntityException;
import com.chumachenko.coursework.repository.organization.OrgRepoImpl;
import com.chumachenko.coursework.repository.orgdata.OrgDataRepoImpl;
import com.chumachenko.coursework.repository.orgdata.OrgDataRepository;
import com.chumachenko.coursework.repository.role.RoleRepository;
import com.chumachenko.coursework.repository.user.UserRepoImpl;
import com.chumachenko.coursework.repository.user.UserRepository;
import com.chumachenko.coursework.util.ChangeScene;
import com.chumachenko.coursework.util.Options;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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
   @FXML
    public Button userToAdminBtn;
    @FXML
    public AnchorPane orgOptionsPane;
    @FXML
    public Button calcLiquidBtn;
    @FXML
    public Button calcSolvencyBtn;
    @FXML
    public Button editOrgDataBtn;
    @FXML
    public Label liquidityLabel;
    @FXML
    public AnchorPane addLiquiDataPane;
    @FXML
    public TextField bankrollField;
    @FXML
    public TextField shortRecFiled;
    @FXML
    public TextField shortLiaField;
    @FXML
    public TextField shortInvFiled;
    @FXML
    public Button saveLiquiDataBtn;

    @FXML
    public Button backToListOfOrgsBtn;
    @FXML
    public Label solvencyLabel;
    @FXML
    public AnchorPane addSolvencyDataPane;
    @FXML
    public TextField intaligbleAssetsField;
    @FXML
    public TextField mainAssetsField;
    @FXML
    public TextField borrowedFundsField;
    @FXML
    public TextField prodReversesField;
    @FXML
    public TextField unfinishedProdField;
    @FXML
    public TextField finishedProdField;
    @FXML
    public Button saveSolvencyDataBtn;


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
        userToAdminBtn.setOnAction(this::switchToAdmin);
        listOfOrg.setOnMouseClicked(this::orgOptions);
        orgOptionsPane.setVisible(false);
        addLiquiDataPane.setVisible(false);
        addSolvencyDataPane.setVisible(false);
    }

    public void initUser(User u){
        user=u;
        userPreview.setText(user.getEmail());
        userToAdminBtn.setVisible(user.getRoleId() != 1);
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
            }catch (RuntimeException e){
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


    public void switchToAdmin(ActionEvent event){
        Stage stage=(Stage) userToAdminBtn.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/admin_menu.fxml")));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminController adminController=loader.getController();
        adminController.initAdmin(user);
        stage.setTitle("Menu");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public void orgOptions(MouseEvent event){
        if(event.getButton()== MouseButton.PRIMARY){
            liquidityLabel.setText(" ");
            solvencyLabel.setText(" ");
            orgOptionsPane.setVisible(true);
            backToListOfOrgsBtn.setOnAction(event2 -> orgOptionsPane.setVisible(false));
            OrgRepository orgRepository=new OrgRepoImpl();
            String selected=listOfOrg.getSelectionModel().getSelectedItem();
            StringBuilder name=new StringBuilder();
            int countBlank=0;
            for(int i=0;i<selected.length()-1;i++){ //считываем название организации из строки вывода
                if(selected.charAt(i)==' ')countBlank++;
                if(countBlank==2){
                    name.append(selected.charAt(i+1));
                }
            }
            final Organization[] organization = {orgRepository.findByUserIdAndName(user.getId(), name.toString())};
            calcLiquidBtn.setOnAction(event1 -> {
                try(OrgDataRepository orgDataRepository=new OrgDataRepoImpl()){
                    if(orgDataRepository.isThisOrgPresent(organization[0].getId())){
                        if(organization[0].getLiquidity()==0.0){
                            updateLiquiData(organization,orgRepository, name.toString(), orgDataRepository, true);
                            organization[0] =orgRepository.findByUserIdAndName(organization[0].getUserId(), name.toString());
                        }
                        liquidityLabel.setText("Коэффициент общей ликвидности:"+ organization[0].getLiquidity().toString());
                    }
                    else{
                       updateLiquiData(organization, orgRepository, name.toString(), orgDataRepository, false);
                       organization[0] =orgRepository.findByUserIdAndName(organization[0].getUserId(), name.toString());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            calcSolvencyBtn.setOnAction(event1 ->{

                try(OrgDataRepository orgDataRepository=new OrgDataRepoImpl()){
                    if(orgDataRepository.isThisOrgPresent(organization[0].getId())){
                        if(organization[0].getSolvency()==0.0){
                            updateSolvencyData(organization,orgDataRepository,orgRepository,name.toString(),true);
                            organization[0] =orgRepository.findByUserIdAndName(organization[0].getUserId(), name.toString());
                        }
                        solvencyLabel.setText("Коэффициент общей плат-ти:"+ organization[0].getSolvency().toString());

                    }
                    else{
                        updateSolvencyData(organization,orgDataRepository,orgRepository,name.toString(),false);
                        organization[0] =orgRepository.findByUserIdAndName(organization[0].getUserId(), name.toString());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public void updateLiquiData(Organization[] organization, OrgRepository orgRepository, String name, OrgDataRepository orgDataRepository, boolean isPresent){
        addLiquiDataPane.setVisible(true);
        saveLiquiDataBtn.setOnAction(event2 -> {
            if(
                    bankrollField.getText().isEmpty()||shortLiaField.getText().isEmpty()||
                            shortRecFiled.getText().isEmpty()||shortInvFiled.getText().isEmpty()
            ){
                Options.showAlert(Alert.AlertType.ERROR, saveLiquiDataBtn.getScene().getWindow(),
                        "Ошибка","Заполните все поля"
                );
                return;
            }
            OrgData orgData=new OrgData();
            orgData.setOrgId(organization[0].getId());
            orgData.setBankroll(Double.parseDouble(bankrollField.getText()));
            orgData.setShortReceivables(Double.parseDouble(shortRecFiled.getText()));
            orgData.setShortInvestments(Double.parseDouble(shortInvFiled.getText()));
            orgData.setShortLiabilities(Double.parseDouble(shortLiaField.getText()));
            if(isPresent){
                orgDataRepository.updateData(orgData);
            }
            else{
                orgDataRepository.create(orgData);
            }
            addLiquiDataPane.setVisible(false);
            organization[0] =orgRepository.findByUserIdAndName(organization[0].getUserId(), name);
            liquidityLabel.setText("Коэффициент общей ликвидности:"+organization[0].getLiquidity().toString());
            bankrollField.setText(" ");
            shortLiaField.setText(" ");
            shortRecFiled.setText(" ");
            shortInvFiled.setText(" ");
        });
    }

    public void updateSolvencyData(Organization[]organization, OrgDataRepository orgDataRepository, OrgRepository orgRepository, String name, boolean isPresent){
        addSolvencyDataPane.setVisible(true);
        saveSolvencyDataBtn.setOnAction(event2 -> {
            if(
                            intaligbleAssetsField.getText().isEmpty()||mainAssetsField.getText().isEmpty()||
                            unfinishedProdField.getText().isEmpty()||prodReversesField.getText().isEmpty() ||
                            finishedProdField.getText().isEmpty()||borrowedFundsField.getText().isEmpty()
            ){
                Options.showAlert(Alert.AlertType.ERROR, saveLiquiDataBtn.getScene().getWindow(),
                        "Ошибка","Заполните все поля"
                );
                return;
            }
            OrgData orgData=new OrgData();
            orgData.setOrgId(organization[0].getId());
            orgData.setIntangibleAssets(Double.parseDouble(intaligbleAssetsField.getText()));
            orgData.setMainAssets(Double.parseDouble(mainAssetsField.getText()));
            orgData.setProdReverses(Double.parseDouble(prodReversesField.getText()));
            orgData.setUnfinishedProduction(Double.parseDouble(unfinishedProdField.getText()));
            orgData.setFinishedProducts(Double.parseDouble(finishedProdField.getText()));
            orgData.setBorrowedFunds(Double.parseDouble(borrowedFundsField.getText()));
            if(isPresent){
                orgDataRepository.updateData(orgData);
            }
            else{
                orgDataRepository.create(orgData);
            }
            addSolvencyDataPane.setVisible(false);
            organization[0] =orgRepository.findByUserIdAndName(organization[0].getUserId(), name);
            solvencyLabel.setText("Коэффициент общей плат-ти:"+organization[0].getSolvency().toString());
            intaligbleAssetsField.setText("");
            mainAssetsField.setText("");
            prodReversesField.setText("");
            unfinishedProdField.setText("");
            finishedProdField.setText("");
            borrowedFundsField.setText(" ");
        });
    }
}
