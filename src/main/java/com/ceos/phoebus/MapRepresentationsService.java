package com.ceos.phoebus;

import java.util.HashMap;
import java.util.Map;
import org.csstudio.display.builder.model.WidgetDescriptor;
import org.csstudio.display.builder.representation.WidgetRepresentation;
import org.csstudio.display.builder.representation.WidgetRepresentationFactory;
import org.csstudio.display.builder.representation.spi.WidgetRepresentationsService;

import static java.util.Map.entry;

/**
 *
 * @author Daniel
 *
 * Register the widget representation factory for maps.
 *
 */
public class MapRepresentationsService implements WidgetRepresentationsService {

    @Override
    public <TWP, TW> Map<WidgetDescriptor, WidgetRepresentationFactory<TWP, TW>> getWidgetRepresentationFactories() {
        return Map.ofEntries(
                entry(new MapDescriptor(), () -> (WidgetRepresentation) new MapRepresentation()));
    }
}
