package com.ceos.display.model;

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
        super(MapWidget.WIDGET_TYPE, WidgetCategory.MONITOR, "Map", "/com/ceos/display/model/map.png", "Mapa con marcadores");
        System.out.println(">>> MapDescriptor loaded for type: " + MapWidget.WIDGET_TYPE);
    }
    
    @Override
    public Widget createWidget() {
        return new MapWidget();
    }
    
    @Override
    public String getType(){
        return MapWidget.WIDGET_TYPE;
    }
    
    @Override
    public String getName(){
        return "Map";
    }
    
    @Override
    public WidgetCategory getCategory(){
       return WidgetCategory.MONITOR; 
    }
   
    @Override
    public String getDescription(){
        return "Mapa con marcadores.";
    }
    
    @Override
    public URL getIconURL() throws Exception {
        return getClass().getResource("/com/ceos/display/model/map.png");
    }
    
}
