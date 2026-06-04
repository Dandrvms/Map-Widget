package com.ceos.display.representation;

import com.ceos.display.model.MapDescriptor;
import java.util.HashMap;
import java.util.Map;
import org.csstudio.display.builder.model.WidgetDescriptor;
import org.csstudio.display.builder.model.WidgetFactory;
import org.csstudio.display.builder.representation.WidgetRepresentationFactory;

/**
 *
 * @author Daniel
 */
public class WidgetSuiteService {
    static {
        System.out.println("###################################");
        System.out.println("#    Cargando Suite de Widgets    #");
        System.out.println("###################################");
        WidgetFactory factory = WidgetFactory.getInstance();
        factory.addWidgetType(new MapDescriptor());
    }
    
    public Map<WidgetDescriptor, WidgetRepresentationFactory<?,?>> getWidgetRepresentationFactories() {
        Map<WidgetDescriptor, WidgetRepresentationFactory<?,?>> suite = new HashMap<>();
        
        suite.put(new MapDescriptor(), new MapRepresentationFactory());
        
        return suite;
    }
}
