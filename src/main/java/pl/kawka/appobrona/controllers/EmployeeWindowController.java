package pl.kawka.appobrona.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import pl.kawka.appobrona.GraduationWorkAppClient;
import pl.kawka.appobrona.model.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class EmployeeWindowController {

    private static final Logger logger = LoggerFactory.getLogger(GraduationWorkAppClient.class);

    private MainWindowController mainWindowController;

    @FXML
    private TextField fxidFieldId, fxidFieldFirstName, fxidFieldLastName, fxidFieldTown, fxidFieldStreet, fxidFieldPostcode,
            fxidFieldTelephoneNumber, fxidFieldNip, fxidFieldDateAdded;

    @FXML
    private Label lblBadDateAdded, fxidLblUpdateCustomer, fxidLblDeleteCustomer;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> id, firstName, lastName, town, street, postcode, telephoneNumber, nip, dateAdded;

    @FXML
    public void readDatabase() {

        System.out.println("********** Wczytanie bazy danych klientów **********");
        try {
            URL url = new URL("http://localhost:8080/api/customer");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONParser parser = new JSONParser();
            JSONArray jSONArray = (JSONArray) parser.parse(in.readLine());

            ObservableList<Customer> masterData = FXCollections.observableArrayList();
            for (Object object : jSONArray) {
                JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                //System.out.println("jSONObject : " + jSONObject.toJSONString());
                masterData.add(new Customer(
                        Integer.parseInt(jSONObject.get("id").toString()),
                        jSONObject.get("firstName").toString(),
                        jSONObject.get("lastName").toString(),
                        jSONObject.get("town").toString(),
                        jSONObject.get("street").toString(),
                        jSONObject.get("postcode").toString(),
                        jSONObject.get("telephoneNumber").toString(),
                        jSONObject.get("nip").toString(),
                        jSONObject.get("dateAdded").toString()));

                id.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
                firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                town.setCellValueFactory(new PropertyValueFactory<Customer, String>("town"));
                street.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                postcode.setCellValueFactory(new PropertyValueFactory<Customer, String>("postcode"));
                telephoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("telephoneNumber"));
                nip.setCellValueFactory(new PropertyValueFactory<Customer, String>("nip"));
                dateAdded.setCellValueFactory(new PropertyValueFactory<Customer, String>("dateAdded"));
            }
            customerTableView.setItems(masterData);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void actionOpenCreateCustomerWindow() {
        Parent root = null;
        Stage secondStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/CustomerCreateWindow.fxml"));
        } catch (IOException ex) {
        }
        secondStage.setScene(new Scene(root, 440, 440));
        secondStage.setMinWidth(440);
        secondStage.setMinHeight(440);
        secondStage.setTitle("Dodawanie klienta");
        secondStage.show();
        logger.info("Wczytanie CreateCustomerWindow");
    }

    @FXML
    public void actionReadCustomers() {
        System.out.println("********** Wczytanie wyszukanych klientów **********");

        JSONObject json = new JSONObject();
        try {
            if(fxidFieldId.getText().isEmpty()){
                json.put("id", 0);
            }else{
                json.put("id", Integer.parseInt(fxidFieldId.getText()));
            }
        } catch (NumberFormatException e) {
            json.put("id", -1);
        }

        json.put("firstName", fxidFieldFirstName.getText());
        json.put("lastName", fxidFieldLastName.getText());
        json.put("town", fxidFieldTown.getText());
        json.put("street", fxidFieldStreet.getText());
        json.put("postcode", fxidFieldPostcode.getText());
        json.put("telephoneNumber", fxidFieldTelephoneNumber.getText());
        json.put("nip", fxidFieldNip.getText());

        lblBadDateAdded.setText("");
        if (fxidFieldDateAdded.getText().isEmpty()) {
            json.put("dateAdded", fxidFieldDateAdded.getText());
            //System.out.println("pusty");
        } else {
            try {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fxidFieldDateAdded.getText());
                json.put("dateAdded", fxidFieldDateAdded.getText());
            } catch (ParseException e) {
                System.out.println("zła data");
                //e.printStackTrace();
                lblBadDateAdded.setText("Zła data!");
                json.put("dateAdded", "0000-00-00 00:00:00");
            }
        }

        //System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/customer/read");
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
            JSONParser parser = new JSONParser();
            JSONArray jSONArray = (JSONArray) parser.parse(in.readLine());

            ObservableList<Customer> masterData = FXCollections.observableArrayList();
            for (Object object : jSONArray) {
                JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                //System.out.println("jSONObject : " + jSONObject.toJSONString());
                masterData.add(new Customer(
                        Integer.parseInt(jSONObject.get("id").toString()),
                        jSONObject.get("firstName").toString(),
                        jSONObject.get("lastName").toString(),
                        jSONObject.get("town").toString(),
                        jSONObject.get("street").toString(),
                        jSONObject.get("postcode").toString(),
                        jSONObject.get("telephoneNumber").toString(),
                        jSONObject.get("nip").toString(),
                        jSONObject.get("dateAdded").toString()));

                id.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
                firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                town.setCellValueFactory(new PropertyValueFactory<Customer, String>("town"));
                street.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                postcode.setCellValueFactory(new PropertyValueFactory<Customer, String>("postcode"));
                telephoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("telephoneNumber"));
                nip.setCellValueFactory(new PropertyValueFactory<Customer, String>("nip"));
                dateAdded.setCellValueFactory(new PropertyValueFactory<Customer, String>("dateAdded"));
            }
            customerTableView.setItems(masterData);

        } catch (Exception ex) {
            lblBadDateAdded.setText("Zła data!");
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }
    }


    @FXML
    public void actionOpenUpdateCustomerWindow() {
        fxidLblUpdateCustomer.setText("");
        fxidLblDeleteCustomer.setText("");

        Stage secondStage = new Stage();
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/CustomerUpdateWindow.fxml"));
            secondStage.setScene(new Scene(loader.load(), 440, 440));
        } catch (IOException ex) {
        }
        secondStage.setMinWidth(440);
        secondStage.setMinHeight(440);
        secondStage.setTitle("Modyfikacja klienta");

        CustomerUpdateController controller = loader.<CustomerUpdateController>getController();

        fxidLblUpdateCustomer.setText("");
        Customer customerSelectedInTable;
        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            customerSelectedInTable = customerTableView.getSelectionModel().getSelectedItem();
            //System.out.println("ID wybranego klienta: " + customerSelectedInTable.getId());
            controller.initData(customerSelectedInTable);
            secondStage.show();
        } else {
            fxidLblUpdateCustomer.setText("Nie wybrano klienta w tabeli!");
            System.out.println("Nie wybrano klienta w tabeli do modyfikacji");
        }
        logger.info("Wczytanie UpdateCustomerWindow");
    }

    @FXML
    public void actionDeleteCustomer() {
        fxidLblUpdateCustomer.setText("");
        fxidLblDeleteCustomer.setText("");

        System.out.println("********** Usunięcie klienta **********");

        Customer selectedCustomer = null;
        /* customerTableView.requestFocus();
        customerTableView.getSelectionModel().select(2);
        System.out.println(customerTableView.getFocusModel().focus(2));*/
        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            //System.out.println(selectedCustomer.getId());

            System.out.println("ID wybranego klienta do usunięcia: " + selectedCustomer.getId());
            JSONObject json = new JSONObject();
            json.put("id", selectedCustomer.getId().toString());

            //System.out.println(json);

            try {
                URL url = new URL("http://localhost:8080/api/customer/delete");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("DELETE");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                wr.flush();
                wr.close();
                conn.getInputStream();

                readDatabase();
            } catch (Exception ex) {
                ex.printStackTrace();
                //logger.error("Loading Application Error.", ex);
            }
        } else{
            System.out.println("Nie wybrano klienta w tabeli do usunięcia");
            fxidLblDeleteCustomer.setText("Nie wybrano pracownika w tabeli!");
        }



    }

    @FXML
    public void back() {
        mainWindowController.loadLoginScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }


}
