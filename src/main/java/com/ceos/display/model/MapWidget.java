package com.ceos.display.model;

import java.util.List;
import org.csstudio.display.builder.model.Widget;
import org.csstudio.display.builder.model.WidgetProperty;

/**
 *
 * @author Daniel
 */
public class MapWidget extends Widget {
    public static final String WIDGET_TYPE = "map";
    
    public MapWidget(){
        super(WIDGET_TYPE);
    }
    
    @Override
    protected void defineProperties(final List<WidgetProperty<?>> properties) {
        super.defineProperties(properties);
    }
}
