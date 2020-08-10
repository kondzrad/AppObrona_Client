package pl.kawka.appobrona5.controllers;


import org.springframework.stereotype.Controller;

@Controller
public class EmployeeWindowController {

    private MainWindowController mainWindowController;


    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
