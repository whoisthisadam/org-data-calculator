package com.chumachenko.orgsinfo.application.controller;

import com.chumachenko.orgsinfo.application.AlertManager;
import com.chumachenko.orgsinfo.application.ChangeScene;
import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;
import commands.fromserver.ResponseFromServer;
import entities.Formula;
import entities.OrgData;
import entities.Organization;
import entities.User;
import entities.enums.OrgType;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class UserController implements Initializable, Connectionable {


    ClientConnection access;
    @Override
    public void setAccess(ClientConnection access) {
        this.access=access;
    }

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
    public Button showFormulesBtn;
    @FXML
    public Label liquidityLabel;
    @FXML
    public AnchorPane addLiquiDataPane;
    @FXML
    public TextField bankrollField;
    @FXML
    public TextField shortRecField;
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
   @FXML
   public AnchorPane top10LiquidPane;
   @FXML
   public ListView<String> top10ListView;

   @FXML
   public Button liquiBtn;
   @FXML
   public Label middleLiquiLabel;

   @FXML
   public Label middleSolvencyLabel;

   @FXML
   public Label solvencyFormulaLabel;

   @FXML
   public Label liquidFormulaLabel;

   @FXML
   public Button backFromFormules;

   @FXML
    public AnchorPane formulesPane;

    @FXML
   private Button solvencyBtn;

    @FXML
    public AnchorPane orgSearchPane;

    @FXML
    public Button orgSearchBtn;

    @FXML
    public TextField searchOrgField;

    @FXML
    public Label searchedOrgLabel;

    @FXML
    public Button okaySearchOrgBtn;

    @FXML
    public Button backFromSearchBtn;


    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profile.setVisible(false);
        editEmail.setVisible(false);
        editName.setVisible(false);
        saveChangesBtn.setVisible(false);
        changePasswordPane.setVisible(false);
        showProfileBtn.setOnAction(this::onProfileClicked);
        logOutBtn.setOnAction(event -> ChangeScene.changeScene(event,"/fxml/login_form.fxml", "User login", access));
        editData.setOnAction(this::onEditClicked);
        changePasswordLink.setOnAction(this::changePassword);
        organizationsPane.setVisible(false);
        showMyOrgBtn.setOnAction(this::organizations);
        setContextMenuDeleteOrg(listOfOrg);
        userToAdminBtn.setOnAction(this::switchToAdmin);
        listOfOrg.setOnMouseClicked(this::orgOptions);
        orgOptionsPane.setVisible(false);
        addLiquiDataPane.setVisible(false);
        addSolvencyDataPane.setVisible(false);
        top10LiquidPane.setVisible(false);
        top10Btn.setOnAction(this::showOrganizationsInfo);
        formulesPane.setVisible(false);
        showFormulesBtn.setOnAction(this::showFormules);
        orgSearchPane.setVisible(false);
        orgSearchBtn.setOnAction(this::searchOrganization);
    }

    public void initUser(User u){
        user=u;
        userPreview.setText(user.getEmail());
        userToAdminBtn.setVisible(user.getRoleId() != 1);
    }


    public void onProfileClicked(ActionEvent event){
        if(organizationsPane.isVisible())organizationsPane.setVisible(false);
        if(top10LiquidPane.isVisible())top10LiquidPane.setVisible(false);
        if(!profile.isVisible()){
            userNameAndSurname.setText(user.getFirstName()+' '+user.getLastName());
            userEmail.setText(user.getEmail());
            try {
                userRole.setText(access.getRoleById(user.getRoleId()).getName().toString().substring(5));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
                    try {
                        ResponseFromServer response=access.updateNameAndEmail(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
                        if(response == ResponseFromServer.SUCCESFULLY) System.out.println("User number "+user.getId().toString()+" succesfully changed his data");
                        else System.out.println("Error changing data by user number "+user.getId());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
                    AlertManager.showAlert(Alert.AlertType.ERROR, window,"Error",
                            "Пожалуйста, введите старый пароль");
                    return;
                }
                if(newPasswordField.getText().isEmpty()){
                    AlertManager.showAlert(Alert.AlertType.ERROR, window,"Error",
                            "Пожалуйста, введите новый пароль");
                    return;
                }
                if(encoder.matches(oldPasswordField.getText(), user.getPassword())){
                    user.setPassword(encoder.encode(newPasswordField.getText()));
                    ResponseFromServer response= null;
                    try {
                        response = access.updatePassword(user.getId(), user.getPassword());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if(response == ResponseFromServer.SUCCESFULLY){
                        resOfChangePassword.setText("Пароль успешно изменен");
                        resOfChangePassword.setVisible(true);
                        finishPasswordChange.setVisible(true);
                        finishPasswordChange.setText("Продолжить");
                        finishPasswordChange.setStyle("-fx-background-color: #90ee90");
                        finishPasswordChange.setOnAction(event2 -> changePasswordPane.setVisible(false));
                    }
                    else System.out.println("Error with changing password by user number"+user.getId());
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
        if(top10LiquidPane.isVisible())top10LiquidPane.setVisible(false);
        if(profile.isVisible())profile.setVisible(false);
        if(!organizationsPane.isVisible()) {
            organizationsPane.setVisible(true);
            addOrgPane.setVisible(false);
            Long numberOfOrgs;
            try {
                numberOfOrgs = access.getNumberOfOrgsUser(user.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (numberOfOrgs != 0) {
                setListOfOrg();
                ObservableList<String> orgListItems = listOfOrg.getItems();
                addOrgBtn.setOnAction(event1 -> addOrganization(event, orgListItems));
                noOrgInfo.setVisible(false);
            } else {
                noOrgInfo.setVisible(true);
                ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>());
                addOrgBtn.setOnAction(event1 -> addOrganization(event, observableList));
            }
        }
        else {
            organizationsPane.setVisible(false);
        }
    }


    public void setListOfOrg(){
        List<Integer> numbers=new ArrayList<>();
        Long numberOfOrgs;
        try {
            numberOfOrgs= access.getNumberOfOrgsUser(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for(int i=1;i<=numberOfOrgs;i++){
            numbers.add(i);
        }
        List<Organization>orgList;
        try {
            orgList = access.findAllOrgsByUserId(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObservableList<String>orgListItems=FXCollections.observableList(orgList
                .stream()
                .map(x->numbers.get(orgList.indexOf(x))+". "+x.getType().toString()+' '+x.getName())
                .collect(Collectors.toList()));
        listOfOrg.setItems(orgListItems);

    }

    public void addOrganization(ActionEvent event, ObservableList<String>observableList){
        addOrgPane.setVisible(true);
        submitAdd.setOnAction(event1 -> {
            Window window=oldPasswordField.getScene().getWindow();
            if (orgTypeField.getText().isEmpty()) {
                    AlertManager.showAlert(Alert.AlertType.ERROR, window,"Error",
                            "Введите тип организации");
                    return;
                }
            if(orgNameField.getText().isEmpty()){
                    AlertManager.showAlert(Alert.AlertType.ERROR, window,"Error",
                        "Введите название организации");
                    return;
                }
            Organization organization=new Organization();
            try {
                organization.setType(OrgType.valueOf(orgTypeField.getText()));
            }
            catch (IllegalArgumentException e){
                AlertManager.showAlert(Alert.AlertType.ERROR, window, "Error",
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
            try {
                ResponseFromServer response=access.createOrganization(organization);
                if(response==ResponseFromServer.SUCCESFULLY){
                    System.out.println("Organization "+organization.getName()+"created by user number "+organization.getUserId());
                }
                else {
                    AlertManager.showAlert(Alert.AlertType.ERROR, window, "Ошибка",
                            "Вы уже добавляли организацию с таким юридическим именем");
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        if(listOfOrg.getItems().size()==1){
            AlertManager.showAlert(Alert.AlertType.ERROR, listOfOrg.getScene().getWindow(), "Ошибка",
                    "Минимум одна организация должна быть после добавления");
            return;
        }
        String selected=listOfOrg.getSelectionModel().getSelectedItem();
        StringBuilder name=new StringBuilder();
        int countBlank=0;
        for(int i=0;i<selected.length()-1;i++){ //считываем название организации из строки вывода
            if(selected.charAt(i)==' ')countBlank++;
            if(countBlank>1){
                name.append(selected.charAt(i+1));
            }
        }
        try {
            ResponseFromServer response=access.deleteOrganization(user.getId(), name.toString());
            if(response==ResponseFromServer.SUCCESFULLY) System.out.println("User "+user.getEmail()+" deleted organization "+name);
            else System.out.println("Error deleting organization by user "+user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setListOfOrg();
    }

    public void setContextMenuDeleteOrg(ListView listView){
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
        adminController.setAccess(access);
        stage.setTitle("Menu");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

   public void orgOptions(MouseEvent event){
        if(event.getButton()== MouseButton.PRIMARY){
            liquidityLabel.setText(" ");
            solvencyLabel.setText(" ");
            orgOptionsPane.setVisible(true);
            backToListOfOrgsBtn.setOnAction(event2 -> {orgOptionsPane.setVisible(false);});
            String selected=listOfOrg.getSelectionModel().getSelectedItem();
            StringBuilder name=new StringBuilder();
            int countBlank=0;
            for(int i=0;i<selected.length()-1;i++){ //считываем название организации из строки вывода
                if(selected.charAt(i)==' ')countBlank++;
                if(countBlank>1){
                    name.append(selected.charAt(i+1));
                }
            }
            thingsWithOrgs(name.toString());
        }
    }

    public void thingsWithOrgs(String name){
        final Organization[] organization;
        try {
            organization = new Organization[]{access.findOrgByUserIdAndName(user.getId(), name)};
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setContextMenuEditLiquiData(calcLiquidBtn, organization);
        setContextMenuEditSolvencyData(calcSolvencyBtn, organization);
        calcLiquidBtn.setOnAction(event1 -> {
            boolean isThisOrgPresent;
            try {
                isThisOrgPresent=access.isThisOrgPresent(organization[0].getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(isThisOrgPresent){
                if(organization[0].getLiquidity()==0.0){
                    updateLiquiData(organization, name.toString(), true);
                    try {
                        organization[0] =access.findOrgByUserIdAndName(organization[0].getUserId(), name);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                liquidityLabel.setText("Коэффициент общей ликвидности:"+ organization[0].getLiquidity().toString());
            }
            else{
                updateLiquiData(organization, name.toString(), false);
                try {
                    organization[0] =access.findOrgByUserIdAndName(organization[0].getUserId(), name);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        calcSolvencyBtn.setOnAction(event1 ->{

            boolean isThisOrgPresent;
            try {
                isThisOrgPresent=access.isThisOrgPresent(organization[0].getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if(isThisOrgPresent) {
                if (organization[0].getSolvency() == 0.0) {
                    updateSolvencyData(organization, name.toString(), true);
                    try {
                        organization[0] = access.findOrgByUserIdAndName(organization[0].getUserId(), name);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                solvencyLabel.setText("Коэффициент общей плат-ти:" + organization[0].getSolvency().toString());

            }
            else{
                updateSolvencyData(organization ,name.toString(),false);
                try {
                    organization[0] =access.findOrgByUserIdAndName(organization[0].getUserId(), name);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void updateLiquiData(Organization[] organization, String name, boolean isPresent){
        addLiquiDataPane.setVisible(true);
        saveLiquiDataBtn.setOnAction(event2 -> {
            if(
                    bankrollField.getText().isEmpty()||shortLiaField.getText().isEmpty()||
                            shortRecField.getText().isEmpty()||shortInvFiled.getText().isEmpty()
            ){
                AlertManager.showAlert(Alert.AlertType.ERROR, saveLiquiDataBtn.getScene().getWindow(),
                        "Ошибка","Заполните все поля"
                );
                return;
            }
            OrgData orgData=new OrgData();
            orgData.setOrgId(organization[0].getId());
            orgData.setBankroll(Double.parseDouble(bankrollField.getText()));
            orgData.setShortReceivables(Double.parseDouble(shortRecField.getText()));
            orgData.setShortInvestments(Double.parseDouble(shortInvFiled.getText()));
            orgData.setShortLiabilities(Double.parseDouble(shortLiaField.getText()));
            if(isPresent){
                try {
                    access.updateOrgData(orgData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                try {
                    access.createOrgData(orgData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            addLiquiDataPane.setVisible(false);
            try {
                organization[0] =access.findOrgByUserIdAndName(organization[0].getUserId(), name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            liquidityLabel.setText("Коэффициент общей ликвидности:"+organization[0].getLiquidity().toString());
            bankrollField.setText(" ");
            shortLiaField.setText(" ");
            shortRecField.setText(" ");
            shortInvFiled.setText(" ");
        });
    }

    public void updateSolvencyData(Organization[]organization, String name, boolean isPresent){
        addSolvencyDataPane.setVisible(true);
        saveSolvencyDataBtn.setOnAction(event2 -> {
            if(
                            intaligbleAssetsField.getText().isEmpty()||mainAssetsField.getText().isEmpty()||
                            unfinishedProdField.getText().isEmpty()||prodReversesField.getText().isEmpty() ||
                            finishedProdField.getText().isEmpty()||borrowedFundsField.getText().isEmpty()
            ){
                AlertManager.showAlert(Alert.AlertType.ERROR, saveLiquiDataBtn.getScene().getWindow(),
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
                try {
                    access.updateOrgData(orgData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                try {
                    access.createOrgData(orgData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            addSolvencyDataPane.setVisible(false);
            try {
                organization[0] =access.findOrgByUserIdAndName(organization[0].getUserId(), name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            solvencyLabel.setText("Коэффициент общей плат-ти:"+organization[0].getSolvency().toString());
            intaligbleAssetsField.setText("");
            mainAssetsField.setText("");
            prodReversesField.setText("");
            unfinishedProdField.setText("");
            finishedProdField.setText("");
            borrowedFundsField.setText("");
        });
    }

    public void showOrganizationsInfo(ActionEvent event){
        if(profile.isVisible())profile.setVisible(false);
        if(organizationsPane.isVisible())organizationsPane.setVisible(false);
        if(!top10LiquidPane.isVisible()){
            top10LiquidPane.setVisible(true);
            liquiBtn.setOnAction(event1 ->setTop10ListViewLiquidity());
            solvencyBtn.setOnAction(event1 ->setTop10ListViewSolvency());
            setLiquidityLabel(event);
            setSolvencyLabel(event);
        }
        else top10LiquidPane.setVisible(false);
    }


    public void setTop10ListViewLiquidity(){
        AtomicReference<Integer> number= new AtomicReference<>(1);
        List<Organization>organizationList= null;
        try {
            organizationList = access.findTopSortedByLiquidity();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObservableList<String>top10LiquiListItems=
                FXCollections.observableList(
                        organizationList.stream().map(x->{
                            String res= null;
                            try {
                                res = number.toString()+". "+x.getType()+' '+x.getName()+
                                        "(Л:"+x.getLiquidity().toString()+", "+access.getUserById(x.getUserId()).getEmail()+')';
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            number.updateAndGet(v -> v + 1);
                            return res;
                        }).collect(Collectors.toList())
                );
        top10ListView.setItems(top10LiquiListItems);
    }

    public void setTop10ListViewSolvency(){
        AtomicReference<Integer> number= new AtomicReference<>(1);
        List<Organization>organizationList= null;
        try {
            organizationList = access.findTopSortedBySolvency();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObservableList<String>top10SolvencyListItems=
                FXCollections.observableList(
                        organizationList.stream().map(x->{
                            String res= null;
                            try {
                                res = number.toString()+". "+x.getType()+' '+x.getName()+
                                        "(П:"+x.getSolvency().toString()+", "+access.getUserById(x.getUserId()).getEmail()+')';
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            number.updateAndGet(v -> v + 1);
                            return res;
                        }).collect(Collectors.toList())
                );
        top10ListView.setItems(top10SolvencyListItems);
    }

   public void setLiquidityLabel(ActionEvent event){
       Double avgLiquidity;
       try {
           avgLiquidity = access.getAvgLiquidity();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       middleLiquiLabel.setText(middleLiquiLabel.getText()+' '+avgLiquidity.toString());
   }
   public void setSolvencyLabel(ActionEvent event){
       Double avgSolvency;
       try {
           avgSolvency=access.getAvgSolvency();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       middleSolvencyLabel.setText(middleSolvencyLabel.getText()+' '+avgSolvency.toString());
   }

   public void showFormules(ActionEvent event){
       formulesPane.setVisible(true);
       List<Formula>list= null;
       try {
           list = access.getAllFormulas();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       liquidFormulaLabel.setText(list.get(1).getValue());
       solvencyFormulaLabel.setText(list.get(0).getValue());
       backFromFormules.setOnAction(event1->formulesPane.setVisible(false));
   }


   public void searchOrganization(ActionEvent event){
        orgSearchPane.setVisible(true);
        okaySearchOrgBtn.setOnAction(event1 -> {
            if(searchOrgField.getText().isEmpty()){
                AlertManager.showAlert(Alert.AlertType.ERROR, okaySearchOrgBtn.getScene().getWindow(),
                        "Ошибка", "Введите название организации"
                        );
                return;
            }
            String orgName=searchOrgField.getText();
            ResponseFromServer response;
            try {
                response=access.checkIfThisUserHasThisOrg(user.getId(), orgName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(response == ResponseFromServer.ORG_NOT_EXIST){
                searchedOrgLabel.setText("У вас нет организации с таким названием");
            }
            else{
                Organization organization;
                try {
                    organization=access.findOrgByUserIdAndName(user.getId(), orgName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                searchedOrgLabel.setText(organization.getType()+" "+organization.getName());
                searchedOrgLabel.setOnMouseClicked(event2 -> {
                    liquidityLabel.setText(" ");
                    solvencyLabel.setText(" ");
                    orgOptionsPane.setVisible(true);
                    orgSearchPane.setVisible(false);
                    backToListOfOrgsBtn.setOnAction(event3 -> {
                        orgOptionsPane.setVisible(false);
                        orgSearchPane.setVisible(true);
                    ;});
                    thingsWithOrgs(organization.getName());
                });
            }
            backFromSearchBtn.setOnAction(event2 ->orgSearchPane.setVisible(false));
        });
   }

   public void setContextMenuEditLiquiData(Button button, Organization[] organization){
       MenuItem menuItem=new MenuItem("Редактировать данные");
       ContextMenu contextMenu=new ContextMenu(menuItem);
       button.setContextMenu(contextMenu);
       menuItem.setOnAction(event -> editLiquiData(event, organization));
   }

    public void editLiquiData(ActionEvent event, Organization[] organization) {
        addLiquiDataPane.setVisible(true);
        addLiquiDataPane.setVisible(true);
        updateLiquiData(organization, organization[0].getName(), true);
    }

    public void setContextMenuEditSolvencyData(Button button, Organization[] organization){
        MenuItem menuItem=new MenuItem("Редактировать данные");
        ContextMenu contextMenu=new ContextMenu(menuItem);
        button.setContextMenu(contextMenu);
        menuItem.setOnAction(event -> editSolvencyData(event, organization));
    }

    public void editSolvencyData(ActionEvent event, Organization[] organization) {
        addSolvencyDataPane.setVisible(true);
        addSolvencyDataPane.setVisible(true);
        updateSolvencyData(organization, organization[0].getName(), true);
    }


}
