package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class CustomerCreateController {

    @FXML
    private TextField idFieldFirstName, idFieldLastName, idFieldTown, idFieldStreet, idFieldPostcode,
            idFieldTelephoneNumber, idFieldNip;
    @FXML
    private Label lblFirstName, lblLastName, lblTown, lblStreet, lblPostcode, lblTelephoneNumber, lblNip;

    @FXML
    public void actionCreateCustomer() {

        int falseMatcher = 0;
        lblFirstName.setText("");
        lblLastName.setText("");
        lblTown.setText("");
        lblStreet.setText("");
        lblPostcode.setText("");
        lblTelephoneNumber.setText("");
        lblNip.setText("");

        String firstName = idFieldFirstName.getText();
        String lastName = idFieldLastName.getText();
        String town = idFieldTown.getText();
        String street = idFieldStreet.getText();
        String postcode = idFieldPostcode.getText();
        String telephoneNumber = idFieldTelephoneNumber.getText();
        String nip = idFieldNip.getText();

        Pattern compiledPatternString = Pattern.compile("[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,40}");
        Matcher matcherFirstName = compiledPatternString.matcher(firstName);
        Matcher matcherLastName = compiledPatternString.matcher(lastName);
        Matcher matcherTown = compiledPatternString.matcher(town);
        Matcher matcherStreet = compiledPatternString.matcher(street);

        Pattern compiledPatternString2 = Pattern.compile("[0-9]{2}-[0-9]{3}");
        Matcher matcherPostcode = compiledPatternString2.matcher(postcode);

        Pattern compiledPatternString3 = Pattern.compile("[+][0-9]{2} [0-9]{9}");
        Matcher matcherTelephoneNumber = compiledPatternString3.matcher(telephoneNumber);

        Pattern compiledPatternString4 = Pattern.compile("[0-9]{10}");
        Matcher matcherNip = compiledPatternString4.matcher(nip);

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
        if(matcherTown.matches()){
            town = town.substring(0, 1).toUpperCase() + town.substring(1).toLowerCase();
        }else{
            falseMatcher++;
            lblTown.setText("Popraw miasto! 1-40 liter");
        }
        if(matcherStreet.matches() || street.isEmpty()){
           if (!street.isEmpty()){street = street.substring(0, 1).toUpperCase() + street.substring(1).toLowerCase();}
        }else{
            falseMatcher++;
            lblStreet.setText("Popraw ulicę! 1-40 liter");
        }

        if(matcherPostcode.matches() || postcode.isEmpty()){
        }else{
            falseMatcher++;
            lblPostcode.setText("Popraw kod pocztowy! xx-xxx");
        }
        if(matcherTelephoneNumber.matches()){
        }else{
            falseMatcher++;
            lblTelephoneNumber.setText("Popraw numer telefonu! +xx xxxxxxxxx");
        }
        if(matcherNip.matches()){
        }else{
            falseMatcher++;
            lblNip.setText("Popraw NIP! xxxxxxxxxx");
        }

        //System.out.println("False matcher: " + falseMatcher);

        if(falseMatcher==0) {
            System.out.println("********** Tworzenie klienta **********");

            JSONObject json = new JSONObject();
            json.put("id", 0);
            json.put("firstName", firstName);
            json.put("lastName", lastName);
            json.put("town", town);
            json.put("street", street);
            json.put("postcode", postcode);
            json.put("telephoneNumber", telephoneNumber);
            json.put("nip", nip);
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
        }else{
            System.out.println("Wprowadzono złe dane");
        }
    }

}
