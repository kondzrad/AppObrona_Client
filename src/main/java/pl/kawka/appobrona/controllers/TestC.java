package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

@Controller
public class TestC {

    private MainWindowController mainWindowController;

    @FXML
    public void back(){ //nazwa onAction przycisku
        mainWindowController.loadMenuScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
