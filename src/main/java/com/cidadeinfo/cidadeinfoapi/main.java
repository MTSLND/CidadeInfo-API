package com.cidadeinfo.cidadeinfoapi;

import com.cidadeinfo.cidadeinfoapi.FileHandling.FileHandling;
import com.cidadeinfo.cidadeinfoapi.apiHandling.ApiHandling;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;



public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        Image img  = new Image(Objects.requireNonNull(getClass().getResourceAsStream("20220417_225420.jpg")));
        scene.getStylesheets().add(css);
        stage.setTitle("Obter Municipios");
        stage.setResizable(false);
        stage.getIcons().add(img);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> logout(stage));
    }

    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair");
        alert.setHeaderText("Deseja Sair ?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }


    public static void main(String[] args) throws Exception {
        try{
            launch();

           //var apiHandling = new ApiHandling();
           //var fileHandling = new FileHandling(apiHandling);
           //apiHandling.setUF("sp");
           //System.out.println(apiHandling.getCidades());
           //fileHandling.createCSV();

        }catch(Exception e){
            throw new Exception("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}