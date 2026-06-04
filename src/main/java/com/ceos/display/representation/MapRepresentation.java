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
}
