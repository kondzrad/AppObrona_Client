package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class ReadCustomerWindowController {

    @FXML
    private TextField idFieldId, idFieldFirstName, idFieldLastName, idFieldTown, idFieldStreet, idFieldPostcode,
            idFieldTelephoneNumber, idFieldNip, idFieldDateAdded;

    JSONArray jSONArray = null;

    @FXML
    public void actionReadCustomers(){



        System.out.println("Wchodze do wczytania klientow");

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

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/customer/read");
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

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("poszłooooooooooooooooo");
            JSONParser parser = new JSONParser();
            jSONArray = (JSONArray) parser.parse(in.readLine());

            System.out.println("to ta lista: "+ jSONArray);

            EmployeeWindowController employeeWindowController = new EmployeeWindowController();
            //employeeWindowController.xxxx(jSONArray);

        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }

    }


   /* public JSONArray getjSONArray() {
        System.out.println("to ta lista2: "+ jSONArray);
        return jSONArray;
    }*/


}
