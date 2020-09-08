package pl.kawka.appobrona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppObronaClient extends Application {
    private ConfigurableApplicationContext springContext; //do wstrzykiwania zaleznosci
    private Parent rootNode;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(rootNode, 800, 680));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(680);
        primaryStage.setTitle("AppObrona");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(AppObronaClient.class); //przypisanie contextowi start aplikacji
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);//kontrolery za obluge widokow beda tworzone przez
        // spring kontext
        rootNode = fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
