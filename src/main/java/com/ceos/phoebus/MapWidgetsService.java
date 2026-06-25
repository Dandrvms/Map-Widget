package com.ceos.phoebus;

import java.util.Collection;
import java.util.List;
import org.csstudio.display.builder.model.WidgetDescriptor;
import org.csstudio.display.builder.model.spi.WidgetsService;

/**
 *
 * @author Daniel
 */
public class MapWidgetsService implements WidgetsService {

    @Override
    public Collection<WidgetDescriptor> getWidgetDescriptors() {
        return List.of(new MapDescriptor());
    }
    
}
