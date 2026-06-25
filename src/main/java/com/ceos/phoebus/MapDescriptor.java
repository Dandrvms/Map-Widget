package com.ceos.phoebus;

import java.net.URL;
import org.csstudio.display.builder.model.Widget;
import org.csstudio.display.builder.model.WidgetCategory;
import org.csstudio.display.builder.model.WidgetDescriptor;

/**
 *
 * @author Daniel
 */
public class MapDescriptor extends WidgetDescriptor {

    public MapDescriptor(){
        super(MapWidget.WIDGET_TYPE, WidgetCategory.MONITOR, "Map", "/icons/map.png", "Mapa con marcadores");
    }
    
    @Override
    public Widget createWidget() {
        return new MapWidget();
    }    
}
