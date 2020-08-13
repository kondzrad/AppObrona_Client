package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;

@Controller
public class AdminWindowController {

    private MainWindowController mainWindowController;

    @FXML
    public void back(){ //nazwa onAction przycisku
        mainWindowController.loadMenuScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

}
