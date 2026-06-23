package com.ceos.map.ui;

import java.io.File;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** 
 * Select a .bob file and set macros
*/
public class MarkerDialog extends Dialog<Boolean> {

    private final TextField displayField = new TextField();

    public MarkerDialog(Double lat, Double lon) {
        setTitle("Setup Marker");
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/com/ceos/display/model/marker.png")); 
        
        setHeaderText("Choose Phoebus display binding.");




        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label(".bob file:"), 0, 0);
        displayField.setEditable(false);
        grid.add(displayField, 1, 0);
        filePicker(grid);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        grid.add(new Label("Latitude: " + lat), 0, 1);
        grid.add(new Label("Longitude: " + lon), 0, 2);

        setResultConverter(button -> {
            if (button == ButtonType.OK) {
                
                return true;
            }
            return false;
        });
    }
    
    public String getDisplay(){
        String display = this.displayField.getText();
        if(display != null){
            return display;
        }
        return "";
    }
    
    private void filePicker(GridPane grid){
        Button browse = new Button("...");
        
        browse.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Choose display");
            
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Phoebus Display", "*.bob")
            );
            
            File selected = fc.showOpenDialog(getDialogPane().getScene().getWindow());
            
            if (selected != null){
                displayField.setText(selected.getName());
            }
        });
        
        grid.add(browse, 2, 0);
        
       
    }
}