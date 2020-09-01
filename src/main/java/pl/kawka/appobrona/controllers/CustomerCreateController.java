package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class CustomerCreateController {

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldTown, idFieldStreet, idFieldPostcode,
            idFieldTelephoneNumber, idFieldNip;

    @FXML
    public void actionCreateCustomer() {

        System.out.println("********** Tworzenie klienta **********");

        JSONObject json = new JSONObject();
        json.put("id", 0);
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("town", idFieldTown.getText());
        json.put("street", idFieldStreet.getText());
        json.put("postcode", idFieldPostcode.getText());
        json.put("telephoneNumber", idFieldTelephoneNumber.getText());
        json.put("nip", idFieldNip.getText());
        json.put("dateAdded", "");

        //System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/customer/create");
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
