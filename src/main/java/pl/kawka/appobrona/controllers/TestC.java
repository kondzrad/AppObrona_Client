package pl.kawka.appobrona.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class TestC {

    private MainWindowController mainWindowController;

    @FXML
    public void testTest(){

        Parent root = null;
       Stage secondStage = new Stage();
        try{
            root = FXMLLoader.load(getClass().getResource("/fxml/TestW.fxml"));
        } catch(IOException ex){

        }
        secondStage.setScene(new Scene(root, 200, 200));
        secondStage.setMinWidth(200);
        secondStage.setMinHeight(200);
        secondStage.setTitle("Poczatki app2");
        secondStage.show();
        }


    @FXML
    public void back(){ //nazwa onAction przycisku
        mainWindowController.loadMenuScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
