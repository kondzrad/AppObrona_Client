package pl.kawka.appobrona.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import pl.kawka.appobrona.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class AdminWindowController {

    private MainWindowController mainWindowController;

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, String> id, firstName, lastName, status, login, password;

    @FXML
    private TextField idFieldId, idFieldFirstName, idFieldLastName, idFieldStatus, idFieldLogin, idFieldPassword;

    @FXML
    public void readDatabase() {

        System.out.println("Wczytanie bazy danych pracowników");
        try {
            URL url = new URL("http://localhost:8080/api/employee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONParser parser = new JSONParser();
            JSONArray jSONArray = (JSONArray) parser.parse(in.readLine());

            ObservableList<Employee> masterData = FXCollections.observableArrayList();
            for (Object object : jSONArray) {
                JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                System.out.println("jSONObject : " + jSONObject.toJSONString());
                masterData.add(new Employee(
                        Integer.parseInt(jSONObject.get("id").toString()),
                        jSONObject.get("firstName").toString(),
                        jSONObject.get("lastName").toString(),
                        jSONObject.get("status").toString(),
                        jSONObject.get("login").toString(),
                        jSONObject.get("password").toString()));

                id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
                firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
                lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
                status.setCellValueFactory(new PropertyValueFactory<Employee, String>("status"));
                login.setCellValueFactory(new PropertyValueFactory<Employee, String>("login"));
                password.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
            }
            employeeTableView.setItems(masterData);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void actionOpenCreateEmployeeWindow() {
        Parent root = null;
        Stage secondStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/CreateEmployeeWindow.fxml"));
        } catch (IOException ex) {
        }
        secondStage.setScene(new Scene(root, 400, 400));
        secondStage.setMinWidth(400);
        secondStage.setMinHeight(400);
        secondStage.setTitle("Stwórz pracownika");
        secondStage.show();
    }

    @FXML
    public void actionReadCustomers() {

        System.out.println("Wchodze do wczytania pracowników z okna AdminWindow");

        JSONObject json = new JSONObject();
        try {
            json.put("id", Integer.parseInt(idFieldId.getText()));
        } catch (NumberFormatException e) {
            json.put("id", 0);
        }
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("status", idFieldStatus.getText());
        json.put("login", idFieldLogin.getText());
        json.put("password", idFieldPassword.getText());

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/employee/read");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            wr.flush();
            wr.close();
            conn.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONParser parser = new JSONParser();
            JSONArray jSONArray = (JSONArray) parser.parse(in.readLine());

            ObservableList<Employee> masterData = FXCollections.observableArrayList();
            for (Object object : jSONArray) {
                JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                System.out.println("jSONObject : " + jSONObject.toJSONString());
                masterData.add(new Employee(
                        Integer.parseInt(jSONObject.get("id").toString()),
                        jSONObject.get("firstName").toString(),
                        jSONObject.get("lastName").toString(),
                        jSONObject.get("status").toString(),
                        jSONObject.get("login").toString(),
                        jSONObject.get("password").toString()));

                id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
                firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
                lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
                status.setCellValueFactory(new PropertyValueFactory<Employee, String>("status"));
                login.setCellValueFactory(new PropertyValueFactory<Employee, String>("login"));
                password.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
            }
            employeeTableView.setItems(masterData);

        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }
    }

    @FXML
    public void actionOpenUpdateEmployeeWindow() {

        Stage secondStage = new Stage();
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/UpdateEmployeeWindow.fxml"));
            secondStage.setScene(new Scene(loader.load(), 400, 400));
        } catch (IOException ex) {
        }
        secondStage.setMinWidth(400);
        secondStage.setMinHeight(400);
        secondStage.setTitle("Modyfikacja pracownika");

        UpdateEmployeeController controller = loader.<UpdateEmployeeController>getController();

        Employee employeeSelectedInTable = null;
        if (employeeTableView.getSelectionModel().getSelectedItem() != null) {
            employeeSelectedInTable = employeeTableView.getSelectionModel().getSelectedItem();
            System.out.println("ID wybranego pracownika: " + employeeSelectedInTable.getId());
            controller.initData(employeeSelectedInTable); //przeslanie danych do innego okna
            secondStage.show();
        } else {
            System.out.println("Nie wybrano pracownika w tabeli");
        }
    }

    @FXML
    public void actionDeleteEmployee() {

        Employee selectedPerson = null;
        if (employeeTableView.getSelectionModel().getSelectedItem() != null) {
            selectedPerson = employeeTableView.getSelectionModel().getSelectedItem();
            System.out.println(selectedPerson.getId());
        }

        JSONObject json = new JSONObject();
        json.put("id", selectedPerson.getId().toString());
        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/employee/delete");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            wr.flush();
            wr.close();
            conn.getInputStream();

            readDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }
    }

    @FXML
    public void back() { //nazwa onAction przycisku
        mainWindowController.loadLoginScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

}
