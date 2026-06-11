package com.ceos.display.representation;

import com.ceos.display.model.MapDescriptor;
import java.util.HashMap;
import java.util.Map;
import org.csstudio.display.builder.model.WidgetDescriptor;
import org.csstudio.display.builder.model.WidgetFactory;
import org.csstudio.display.builder.representation.WidgetRepresentationFactory;
import org.csstudio.display.builder.representation.spi.WidgetRepresentationsService;

/**
 *
 * @author Daniel
 * 
 * Register the widget representation factory for maps.
 * For some reason, the overwritten code is ignored
 * so I put the static block.
 * 
 */
public class WidgetSuiteService implements WidgetRepresentationsService {
    static {
        WidgetFactory factory = WidgetFactory.getInstance();
        factory.addWidgetType(new MapDescriptor());
    }
    
    @Override
    public Map<WidgetDescriptor, WidgetRepresentationFactory<?,?>> getWidgetRepresentationFactories() {
        Map<WidgetDescriptor, WidgetRepresentationFactory<?,?>> suite = new HashMap<>();
        
        suite.put(new MapDescriptor(), new MapRepresentationFactory());
        
        return suite;
    }
}
