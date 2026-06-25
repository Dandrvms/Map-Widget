package com.ceos.phoebus;

import java.util.HashMap;
import java.util.Map;
import org.csstudio.display.builder.model.WidgetDescriptor;
import org.csstudio.display.builder.representation.WidgetRepresentationFactory;
import org.csstudio.display.builder.representation.spi.WidgetRepresentationsService;

/**
 *
 * @author Daniel
 *
 * Register the widget representation factory for maps.
 *
 */
public class MapRepresentationsService implements WidgetRepresentationsService {

    @Override
    public Map<WidgetDescriptor, WidgetRepresentationFactory<?, ?>> getWidgetRepresentationFactories() {
        Map<WidgetDescriptor, WidgetRepresentationFactory<?, ?>> suite = new HashMap<>();

        suite.put(new MapDescriptor(), new MapRepresentationFactory());

        return suite;
    }
}
