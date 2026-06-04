package com.ceos.display.representation;

import com.ceos.display.model.MapWidget;
import javafx.scene.Parent;
import org.csstudio.display.builder.model.Widget;
import org.csstudio.display.builder.representation.WidgetRepresentation;
import org.csstudio.display.builder.representation.WidgetRepresentationFactory;

/**
 *
 * @author Daniel
 */
public class MapRepresentationFactory implements WidgetRepresentationFactory {

    @Override
    public WidgetRepresentation<Parent, MapWidget, Widget> create() throws Exception {
        return (WidgetRepresentation) new MapRepresentation();
    }
    
}
