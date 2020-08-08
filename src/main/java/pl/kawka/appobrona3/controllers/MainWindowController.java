package pl.kawka.appobrona3.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.kawka.appobrona3.employee.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//@Component
@Controller
public class MainWindowController {

    public MainWindowController(){
        System.out.println("Jestem kontrolerem w kontruktorze");
    }

    @FXML
    private TextField fieldLogin,fieldHaslo;
    @FXML
    private Label statusLogowania;
    @FXML
    private Button przyciskLogowania;


    @FXML
    void initialize(){   }


    @FXML
    private void akcjaLogowania(ActionEvent event){

        JSONObject json = new JSONObject();
        json.put("login", fieldLogin.getText());
        json.put("password", fieldHaslo.getText());


        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/employee2");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); //zeby wyslac jakies obiekt JSON chyba nie da sie z GET bo probowalem
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            System.out.println("poszłooooooooooooooooo1");
            wr.write(json.toString());
            System.out.println("poszłooooooooooooooooo2");
            wr.flush();
            wr.flush();
            wr.close();
            System.out.println("poszłooooooooooooooooo3");
            conn.getInputStream();
            System.out.println("poszłooooooooooooooooo4");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            System.out.println("poszłooooooooooooooooo5");
            String wczytany = in.readLine();
            if (wczytany.equalsIgnoreCase("OKpracownik")){
                System.out.println("ok_pracownik");
                statusLogowania.setText("Jest git!");
            } else if (wczytany.equalsIgnoreCase("OKadmin")) {
                System.out.println("ok_admin");
                statusLogowania.setText("Jest git!");
            } else {
                statusLogowania.setText("Błędne logowanie!");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }







}
