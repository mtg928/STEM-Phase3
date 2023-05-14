/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import com.topfield.JavaFX.ImportColumnSelection3;
import java.awt.Dimension;
import java.awt.EventQueue;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javax.swing.JFrame;
/**
 * @see https://stackoverflow.com/q/41159015/230513
 */
public class LabelTest {

    private void display() {
        JFrame f = new JFrame("LabelTest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFXPanel jfxPanel = new JFXPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 600);
            }
        };
        initJFXPanel(jfxPanel);
        f.add(jfxPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void initJFXPanel(JFXPanel jfxPanel) {
        Platform.runLater(() -> {

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

            jfxPanel.setScene(scene);
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new LabelTest()::display);
    }
}
