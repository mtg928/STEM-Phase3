/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import com.topfield.datamodel.Item;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


 
public class ImportColumnSelection2 extends Application
{

 
    public static void main(String[] args)
    {
        ImportColumnSelection2 importSel = new ImportColumnSelection2();
        importSel.startApp();
    }
 
    @Override
    public void start(Stage stage)
    {
       String[] org = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"};
       String[] match = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"}; 
       ImportColumnSelection3 importSel = new ImportColumnSelection3();
       importSel.setData(org,match);
       
       
       GridPane pane =importSel.getScene();
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> Platform.exit());
        
        Button ok = new Button("OK");
        ok.setOnAction(e -> Platform.exit());
        
        GridPane topGrid = new GridPane();
        topGrid.setHgap(20);
        topGrid.add(ok, 0, 0);
        topGrid.add(cancel, 1, 0);
        
        pane.add(topGrid, 1, 4, 4, 1);
       
        // Create the VBox
        VBox root = new VBox();
        // Add the Pane and The LoggingArea to the VBox
        //root.getChildren().addAll(pane,loggingArea);
        root.getChildren().addAll(pane);
        // Set the Style of the VBox
        root.setStyle("-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: blue;");
 
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        //stage.setScene(scene);
        // Set the Title
        //stage.setTitle("Match your columns");
        // Display the Stage
        //stage.show();
       
       stage.setScene(scene);
        // Set the Title
        stage.setTitle("Match your columns");
        // Display the Stage
        stage.show();
    }
    

    public void startApp(){
     Application.launch();
    }
}
   
