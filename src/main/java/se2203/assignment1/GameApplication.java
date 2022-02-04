package se2203.assignment1;

/*
Piotr Nowak
04-Feb-2022
SE2203-Asn1
pnowak5@uwo.ca
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("HydraGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        scene.getStylesheets().add(getClass().getResource("CustomFont.css").toExternalForm());
        stage.setTitle("Hydra Game");
        stage.getIcons().add(new Image("file:src/main/resources/se2203/assignment1/HydraIcon.png")); //add app icon
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}