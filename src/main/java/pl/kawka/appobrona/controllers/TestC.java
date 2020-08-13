package pl.kawka.appobrona.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class TestC {

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
