package pl.kawka.graduationworkapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class MainWindowController { //kontroler glownego pustego okna

    @FXML
    private AnchorPane mainAnchorPane; //musi byc przypisany fx:id="mainStackPane"

    @FXML
    public void initialize() { //metoda wywolywana zaraz po kontruktorze
        loadLoginScreen();
    }

    public void loadLoginScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LoginWindow.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginWindowController loginWindowController = loader.getController(); //wyciagamy referencje
        loginWindowController.setMainWindowController(this);//przekaznie instancji klasy menu kontroller do inyych
        // (beda sie mogly odnosic do niego)

        //teraz do stackpane glownego wrzucamy Pane z opcjami
        setScreen(pane);
    }

    public void setScreen(Pane pane) {
        //wyrzucalo Null exception poniewaz nie mialem przypisanego: fx:id="mainStackPane" w MainWindow.fxml
        mainAnchorPane.getChildren().clear(); //usuwanie elemntow by kolejne elementy nie naklay sie na siebie po
        // przejsciu na kolejne ekrany
        mainAnchorPane.getChildren().add(pane); //pobieramy dzieci czyli liste elementow co w sobie zawiera
    }

}
