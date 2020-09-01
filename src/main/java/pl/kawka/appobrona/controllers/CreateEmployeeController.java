package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateEmployeeController {

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldStatus, idFieldLogin, idFieldPassword;

    @FXML
    public void actionCreateEmployee() {

        System.out.println("Wchodze do stworzenia klienta");

        JSONObject json = new JSONObject();
        json.put("id", 0);
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("status", idFieldStatus.getText());
        json.put("login", idFieldLogin.getText());
        json.put("password", idFieldPassword.getText());

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/employee/create");
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

        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }

    }

}
