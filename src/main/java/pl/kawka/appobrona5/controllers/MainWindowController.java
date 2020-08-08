package pl.kawka.appobrona5.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainWindowController { //kontroler glownego pustego okna

    @FXML
    private StackPane mainStackPane;

    @FXML
    public void initialize() { //metoda wywolywana zaraz po kontruktorze
        loadMenuScreen();
    }

    public void loadMenuScreen() { //ladowanie ekranu login
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LoginWindow.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginWindowController loginWindowController = loader.getController(); //wyciagamy referencje
        loginWindowController.setMainWindowController(this);//przekaznie instancji klasy menu kontroller do inyych (beda sie mogly
        // odnosic do niego)

        //teraz do stackpane glownego wrzucamy Pane z opcjami
        setScreen(pane);
    }

    public void setScreen(Pane pane) {
        mainStackPane.getChildren().clear(); //usuwanie elemntow by kolejne elementy nie naklay sie na siebie po
        // przejsciu na kolejne ekrany
        mainStackPane.getChildren().add(pane); //pobieramy dzieci czyli liste elementow co w sobie zawiera
    }

}
