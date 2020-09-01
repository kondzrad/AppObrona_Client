package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import pl.kawka.appobrona.model.Employee;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateEmployeeController {

    @FXML
    private Label fxidLabelId;

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldStatus, idFieldLogin, idFieldPassword;

    Integer idWybranegoPracownika;


    void initData(Employee employee) {
        idWybranegoPracownika = employee.getId();
        fxidLabelId.setText("ID wybranego klienta: " + idWybranegoPracownika);
        idFieldFirstName.setText(employee.getFirstName());
        idFieldLastName.setText(employee.getLastName());
        idFieldStatus.setText(employee.getStatus());
        idFieldLogin.setText(employee.getLogin());
        idFieldPassword.setText(employee.getPassword());
    }

    @FXML
    public void actionUpdateEmployee() {

        System.out.println("Wchodze do modyfikacja pracownika");

        JSONObject json = new JSONObject();
        json.put("id", idWybranegoPracownika);
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("status", idFieldStatus.getText());
        json.put("login", idFieldLogin.getText());
        json.put("password", idFieldPassword.getText());

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/employee/update");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); //zeby wyslac jakies obiekt JSON chyba nie da sie z GET bo probowalem
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());  //wyslanie JSON
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
