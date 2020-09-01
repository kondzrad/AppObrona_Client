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


@Controller
public class LoginWindowController {

    private MainWindowController mainWindowController;

    private static final Logger logger = LoggerFactory.getLogger(AppObronaClient.class);

    public LoginWindowController() {
        System.out.println("********** Załadowanie okna logowania **********");
    }

    @FXML
    private TextField fieldLogin, fieldHaslo;
    @FXML
    private Label statusLogowania;

    @FXML
    void initialize() {
    }

    @FXML
    private void akcjaLogowania(ActionEvent event) {

        System.out.println("********** Logowanie **********");

        JSONObject json = new JSONObject();
        json.put("login", fieldLogin.getText());
        json.put("password", fieldHaslo.getText());

        //System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/employee/login");
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
            String readWhoLoggedIn = in.readLine();
            if (readWhoLoggedIn.equalsIgnoreCase("OKpracownik")) {
                System.out.println("Zalogowano pracownika");
                statusLogowania.setText("Zalogowano pracownika!");
                wczytanieEmployeeWindow(); //wczytanie widoku pracownika po poprawnym zalogowaniu
            } else if (readWhoLoggedIn.equalsIgnoreCase("OKadmin")) {
                System.out.println("Zalogowano admina");
                statusLogowania.setText("Zalogowano admina!");
                wczytanieAdminWindow();
            } else {
                statusLogowania.setText("Błędne logowanie!");
                logger.error("Błędne logowanie");
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
        logger.info("Wczytanie AdminWindow");
    }

    @FXML
    public void exit() {
        Platform.exit();
    }


    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
