package com.ceos.map.ui;

import java.io.File;

import com.ceos.map.model.MarkerIcon;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Select a .bob file
 */
public class EditDialog extends Dialog<Boolean> {

    private final TextField displayField = new TextField();
    private final TextField nameField = new TextField();

    public EditDialog(Double lat, Double lon, String name, String display, MarkerIcon icon) {
        setTitle("Edit Marker");
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/marker.png"));

        setHeaderText("Edit marker properties.");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name: "), 0, 0);
        nameField.setText(name);
        grid.add(nameField, 1, 0);
        grid.add(new Label(".bob file:"), 0, 1);
        displayField.setText(display);
        displayField.setEditable(false);
        grid.add(displayField, 1, 1);
        filePicker(grid);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        grid.add(new Label("Latitude: " + lat), 0, 2);
        grid.add(new Label("Longitude: " + lon), 0, 3);

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

    public String getName(){
        String name = this.nameField.getText();
        if(name != null){
            return name;
        }
        return "";
    }

    public MarkerIcon getIcon(){
        return MarkerIcon.DEFAULT;
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

        grid.add(browse, 2, 1);


    }
}