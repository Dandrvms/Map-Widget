package com.ceos.display.ui;

import java.io.File;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.stage.FileChooser;

/** 
 * Select a .bob file and set macros
*/
public class MarkerEditDialog extends Dialog<Boolean> {

    private final TextField displayField = new TextField();
    private final TextArea macrosArea = new TextArea();

    public MarkerEditDialog(MapMarker marker) {
        setTitle("Configurar Acción del Marcador");
        setHeaderText("Define qué pantalla de Phoebus se abrirá al hacer doble clic.");


        displayField.setText(marker.getDisplay());

        String macrosText = marker.getMacros().entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));
        macrosArea.setText(macrosText);
        macrosArea.setPromptText("EJEMPLO:\nPLANTA=Caracas\nID=001");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Archivo .bob:"), 0, 0);
        grid.add(displayField, 1, 0);
        grid.add(new Label("Macros:"), 0, 1);
        grid.add(macrosArea, 1, 1);
        filePicker(grid);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        setResultConverter(button -> {
            if (button == ButtonType.OK) {
                marker.setDisplay(displayField.getText());
                marker.setMacros(parseMacros(macrosArea.getText()));
                return true;
            }
            return false;
        });
    }

    private Map<String, String> parseMacros(String text) {
        Map<String, String> map = new HashMap<>();
        for (String line : text.split("\n")) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                map.put(parts[0].trim(), parts[1].trim());
            }
        }
        return map;
    }
    
    private void filePicker(GridPane grid){
        Button browse = new Button("...");
        
        browse.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar Pantalla");
            
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
