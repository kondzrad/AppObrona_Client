package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateCustomerController {

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldTown, idFieldStreet, idFieldPostcode,
    idFieldTelephoneNumber, idFieldNip;


    @FXML
    public void actionCreateCustomer(){
        JSONObject json = new JSONObject();
        json.put("id", 20);
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("town", idFieldTown.getText());
        json.put("street", idFieldStreet.getText());
        json.put("postcode", idFieldPostcode.getText());
        json.put("telephoneNumber", idFieldTelephoneNumber.getText());
        json.put("nip", idFieldNip.getText());
        json.put("dateAdded", "");

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/customer/create");
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
            /*BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
            }*/

        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }
    }

}
