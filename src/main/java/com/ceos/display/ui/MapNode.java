package com.ceos.display.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


/**
 *
 * @author Daniel
 */
public class MapNode extends StackPane {
    public MapNode(){
        this.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPickOnBounds(true);
    }
}
