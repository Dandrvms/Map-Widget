package com.ceos.display.ui;

import com.ceos.map.ui.MapNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 * 
 * Launch a Standalone app
 */
public class MapSample extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        MapNode node = new MapNode();
        pane.getChildren().add(node);
        Scene scene = new Scene(pane, 400, 400);
        stage.setTitle("Widget Test");
        stage.setScene(scene);
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}
