/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import com.topfield.datamodel.Item;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
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
import javax.swing.JFrame;


 
public class ImportColumnSelection3
{
    // Create the ListViews
    ListView<Item> orginView = new ListView<>();
    ListView<Item> sourceView = new ListView<>();
    ListView<Item> targetView = new ListView<>();
 
    // Set the Custom Data Format
    static final DataFormat FRUIT_LIST = new DataFormat("FruitList");

    public GridPane getScene()
    {
        // Create the Labels
        Label originListLbl = new Label("Template Columns");
        Label targetListLbl = new Label("Match Columns");
        Label sourceListLbl = new Label("Your import file Columns");
        
        Label messageLbl = new Label("Please align your import file columns");
 
        // Set the Size of the Views and the LoggingArea
        orginView.setPrefSize(200, 400);
        sourceView.setPrefSize(200, 400);
        targetView.setPrefSize(200, 400);
 

 
        // Allow multiple-selection in lists
        orginView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sourceView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        targetView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        

 
        // Create the GridPane
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
 
        // Add the Labels and Views to the Pane
        pane.add(messageLbl, 0, 0, 4, 1);
        pane.addRow(1, originListLbl,targetListLbl, sourceListLbl);
        pane.addRow(2, orginView,targetView,sourceView);
        //pane.add(topGrid, 1, 4, 4, 1);

 
        // Add mouse event handlers for the source
        sourceView.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                writelog("Event on Source: drag detected");
                dragDetected(event, sourceView);
            }
        });
 
        sourceView.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag over");
                dragOver(event, sourceView);
            }
        });
 
        sourceView.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag dropped");
                dragDropped(event, sourceView);
            }
        });
 
        sourceView.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag done");
                dragDone(event, sourceView);
            }
        });
 
        // Add mouse event handlers for the target
        targetView.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                writelog("Event on Target: drag detected");
                dragDetected(event, targetView);
            }
        });
 
        targetView.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag over");
                dragOver(event, targetView);
            }
        });
 
        targetView.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag dropped");
                dragDropped(event, targetView);
            }
        });
 
        targetView.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag done");
                dragDone(event, targetView);
            }
        });
 
       
        return pane;
    }
    
    
    public void setData(String[] dataRef,String[] dataSel){
    
        sourceView.getItems().addAll(this.getFruitList(dataRef));
        orginView.getItems().addAll(this.getFruitList(dataSel));
    
    
    }
 
    // Create the Fruit List
    private ObservableList<Item> getFruitList(String[] data)
    {
         ObservableList<Item> list = FXCollections.<Item>observableArrayList();
        
        for (int i = 0; i < data.length; i++) {
            list.add(new Item(i,data[i]));
        }
 
      /*  Item apple = new Item(0,"Line no");
        Item orange = new Item(1,"Subsystem Breakdown Code");
        Item papaya = new Item(2,"Subsystem/ Component");
        Item mango = new Item(3,"Function");
        Item grape = new Item(4,"Phase");
        Item guava = new Item(5,"Failure mode");
 
        list.addAll(apple, orange, papaya, mango, grape, guava);*/
        return list;
    }
 
    private void dragDetected(MouseEvent event, ListView<Item> listView)
    {
        // Make sure at least one item is selected
        int selectedCount = listView.getSelectionModel().getSelectedIndices().size();
 
        if (selectedCount == 0)
        {
            event.consume();
            return;
        }
 
        // Initiate a drag-and-drop gesture
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);
 
        // Put the the selected items to the dragboard
        ArrayList<Item> selectedItems = this.getSelectedFruits(listView);
 
        ClipboardContent content = new ClipboardContent();
        content.put(FRUIT_LIST, selectedItems);
 
        dragboard.setContent(content);
        event.consume();
    }
 
    private void dragOver(DragEvent event, ListView<Item> listView)
    {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();
 
        if (event.getGestureSource() != listView && dragboard.hasContent(FRUIT_LIST))
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
 
        event.consume();
    }
 
    @SuppressWarnings("unchecked")
    private void dragDropped(DragEvent event, ListView<Item> listView)
    {
        boolean dragCompleted = false;
 
        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();
 
        if(dragboard.hasContent(FRUIT_LIST))
        {
            ArrayList<Item> list = (ArrayList<Item>)dragboard.getContent(FRUIT_LIST);
            listView.getItems().addAll(list);
            // Data transfer is successful
            dragCompleted = true;
        }
 
        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }
 
    private void dragDone(DragEvent event, ListView<Item> listView)
    {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();
 
        if (tm == TransferMode.MOVE)
        {
            removeSelectedFruits(listView);
        }
 
        event.consume();
    }
 
    private ArrayList<Item> getSelectedFruits(ListView<Item> listView)
    {
        // Return the list of selected Fruit in an ArratyList, so it is
        // serializable and can be stored in a Dragboard.
        ArrayList<Item> list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
 
        return list;
    }
 
    private void removeSelectedFruits(ListView<Item> listView)
    {
        // Get all selected Fruits in a separate list to avoid the shared list issue
        List<Item> selectedList = new ArrayList<>();
 
        for(Item fruit : listView.getSelectionModel().getSelectedItems())
        {
            selectedList.add(fruit);
        }
 
        // Clear the selection
        listView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        listView.getItems().removeAll(selectedList);
    }
 
    // Helper Method for Logging
    private void writelog(String text)
    {
        System.out.println(text + "\n");
    }
    
    public void getSeletions(){
    
    
    }
   
    
    
}
   

