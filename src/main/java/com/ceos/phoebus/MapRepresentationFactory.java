    package com.ceos.phoebus;

import com.ceos.phoebus.MapRepresentation;
import com.ceos.map.ui.MapNode;
import javafx.scene.Parent;
import org.csstudio.display.builder.model.Widget;
import org.csstudio.display.builder.representation.WidgetRepresentation;
import org.csstudio.display.builder.representation.WidgetRepresentationFactory;

/**
 *
 * @author Daniel
 */
public class MapRepresentationFactory implements WidgetRepresentationFactory <Parent, MapNode> {

    @Override
    public WidgetRepresentation<Parent, MapNode, Widget> create() throws Exception {
        return (WidgetRepresentation) new MapRepresentation();
    }
    
}
