package pl.kawka.appobrona.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeCreateController {

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldLogin, idFieldPassword;

    @FXML
    private ChoiceBox choiceBoxStatus;

    @FXML
    private Label lblFirstName, lblLastName, lblStatus, lblLogin, lblPassword;

    public void initialize(){
        choiceBoxStatus.setTooltip(new Tooltip("Wybierz status"));
        choiceBoxStatus.setItems(FXCollections.observableArrayList(
                "Pracownik", new Separator(), "Admin"));
        choiceBoxStatus.getSelectionModel().selectFirst();
    }

    @FXML
    public void actionCreateEmployee() {
        int falseMatcher = 0;
        lblFirstName.setText("");
        lblLastName.setText("");
        lblStatus.setText("");
        lblLogin.setText("");
        lblPassword.setText("");

        String firstName = idFieldFirstName.getText();
        String lastName = idFieldLastName.getText();
        String status = choiceBoxStatus.getValue().toString();
        String login = idFieldLogin.getText();
        String password = idFieldPassword.getText();

        Pattern compiledPatternString = Pattern.compile("[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,40}");
        Matcher matcherFirstName = compiledPatternString.matcher(firstName);
        Matcher matcherLastName = compiledPatternString.matcher(lastName);
        Matcher matcherStatus = compiledPatternString.matcher(status);

        Pattern compiledPatternString2 = Pattern.compile(".{3,40}");
        Matcher matcherLogin = compiledPatternString2.matcher(login);
        Matcher matcherPassword= compiledPatternString2.matcher(password);

        if(matcherFirstName.matches()){
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        }else{
            falseMatcher++;
            lblFirstName.setText("Popraw imię! 1-40 liter");
        }
        if(matcherLastName.matches()){
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        }else{
            falseMatcher++;
            lblLastName.setText("Popraw nazwisko! 1-40 liter");
        }
        if(matcherStatus.matches()){
            status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        }else{
            falseMatcher++;
            lblStatus.setText("Popraw miasto! 1-40 liter");
        }
        if(matcherLogin.matches()){
        }else{
            falseMatcher++;
            lblLogin.setText("Popraw login! Min. 3 znaki");
        }
        if(matcherPassword.matches()){
        }else{
            falseMatcher++;
            lblPassword.setText("Popraw hasło! Min. 3 znaki");
        }

        if(falseMatcher==0) {
        System.out.println("********** Tworzenie pracownika **********");

        JSONObject json = new JSONObject();
        json.put("id", 0);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("status", status);
        json.put("login", login);
        json.put("password", password);

        //System.out.println(json);

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
        }else{
            System.out.println("Wprowadzono złe dane");
        }

    }

}
