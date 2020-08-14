package pl.kawka.appobrona.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import pl.kawka.appobrona.AppObronaClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

//@Component
@Controller
public class LoginWindowController {

    private MainWindowController mainWindowController; //pole do ustawienia Set

    private static final Logger logger = LoggerFactory.getLogger(AppObronaClient.class);

    public LoginWindowController() {
        System.out.println("Jestem kontrolerem w kontruktorze");
    }

    @FXML
    private TextField fieldLogin, fieldHaslo;
    @FXML
    private Label statusLogowania;
    //@FXML  //zakomentowane bo nie wykonuje na nim dodatkowych rzeczy
    //private Button przyciskLogowania;


    @FXML
    void initialize() {
    }

    @FXML
    private void akcjaLogowania(ActionEvent event) {

        JSONObject json = new JSONObject();
        json.put("login", fieldLogin.getText());
        json.put("password", fieldHaslo.getText());

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); //zeby wyslac jakies obiekt JSON chyba nie da sie z GET bo probowalem
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            wr.flush();
            wr.close();
            conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String wczytany = in.readLine();
            if (wczytany.equalsIgnoreCase("OKpracownik")) {
                System.out.println("ok_pracownik");
                statusLogowania.setText("Jest git!");
                wczytanieEmployeeWindow(); //wczytanie widoku pracownika po poprawnym zalogowaniu
            } else if (wczytany.equalsIgnoreCase("OKadmin")) {
                System.out.println("ok_admin");
                statusLogowania.setText("Jest git!");
                wczytanieAdminWindow();
            } else {
                statusLogowania.setText("Błędne logowanie!");
                //logger.error("Błędne logowanie");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }

    }

    @FXML
    public void wczytanieEmployeeWindow() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/EmployeeWindow.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmployeeWindowController employeeWindowController = loader.getController();
        employeeWindowController.setMainWindowController(mainWindowController);
        mainWindowController.setScreen(pane);
        logger.info("Wczytanie EmployeeWindow");
    }

    @FXML
    public void wczytanieAdminWindow() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AdminWindow.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminWindowController adminWindowController = loader.getController();
        adminWindowController.setMainWindowController(mainWindowController);
        mainWindowController.setScreen(pane);
    }


    @FXML
    public void testA() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/TestW.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestC testC = loader.getController();
        testC.setMainWindowController(mainWindowController);
        mainWindowController.setScreen(pane);

    }

    @FXML
    public void exit(){
        Platform.exit();
    }


    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
