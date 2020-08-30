package pl.kawka.appobrona.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import pl.kawka.appobrona.model.Customer;
import pl.kawka.appobrona.model.Employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            System.out.println("poszłooooooooooooooooo");
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
    public void back(){ //nazwa onAction przycisku
        mainWindowController.loadMenuScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }





}
