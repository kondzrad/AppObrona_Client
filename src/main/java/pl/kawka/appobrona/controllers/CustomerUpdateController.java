package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import pl.kawka.appobrona.model.Customer;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerUpdateController {

    @FXML
    private Label fxidLabelId;

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldTown, idFieldStreet, idFieldPostcode,
            idFieldTelephoneNumber, idFieldNip, idFieldDateAdded;

    Integer idSelectedCustomer;


    void initData(Customer customer) {
        idSelectedCustomer = customer.getId();
        fxidLabelId.setText("ID wybranego klienta: " + idSelectedCustomer);
        idFieldFirstName.setText(customer.getFirstName());
        idFieldLastName.setText(customer.getLastName());
        idFieldTown.setText(customer.getTown());
        idFieldStreet.setText(customer.getStreet());
        idFieldPostcode.setText(customer.getPostcode());
        idFieldTelephoneNumber.setText(customer.getTelephoneNumber());
        idFieldNip.setText(customer.getNip());
        idFieldDateAdded.setText(customer.getDateAdded());
    }

    @FXML
    public void actionUpdateCustomer() {

        System.out.println("********** Modyfikowanie klienta **********");
        System.out.println("ID wybranego klienta do modyfikacji: " + idSelectedCustomer);

        JSONObject json = new JSONObject();
        json.put("id", idSelectedCustomer);
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("town", idFieldTown.getText());
        json.put("street", idFieldStreet.getText());
        json.put("postcode", idFieldPostcode.getText());
        json.put("telephoneNumber", idFieldTelephoneNumber.getText());
        json.put("nip", idFieldNip.getText());
        json.put("dateAdded", idFieldDateAdded.getText());

        //System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/customer/update");
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
