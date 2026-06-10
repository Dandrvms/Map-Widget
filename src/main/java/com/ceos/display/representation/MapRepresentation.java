package com.ceos.display.representation;

import com.ceos.display.model.MapWidget;
import com.ceos.display.ui.MapNode;
import org.csstudio.display.builder.representation.javafx.widgets.JFXBaseRepresentation;

/**
 *
 * @author Daniel
 */
public class MapRepresentation extends JFXBaseRepresentation<MapNode, MapWidget>  {
    
    @Override
    protected MapNode createJFXNode() throws Exception {
       return new MapNode(); 
    }
    
    @Override
    public void updateChanges() {
        super.updateChanges();
        final MapWidget model = model_widget;
        final MapNode node = jfx_node;
        node.setPrefSize(model.propWidth().getValue(), model.propHeight().getValue());
    }

    @Override
    public void registerListeners(){
        super.registerListeners();
        final MapWidget model = model_widget;
        final MapNode node = jfx_node;
        
        model.propWidth().addUntypedPropertyListener((prop, old, val) -> {
            node.setPrefWidth(((Number) val).doubleValue());
        });
        
        model.propHeight().addUntypedPropertyListener((prop, old, val) -> {
            node.setPrefHeight(((Number) val).doubleValue());
        });
        
        node.setPrefSize(model.propWidth().getValue().doubleValue(), model.propHeight().getValue().doubleValue());
    }
}
