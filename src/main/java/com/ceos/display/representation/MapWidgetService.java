package com.ceos.display.representation;

import com.ceos.display.model.MapDescriptor;
import java.util.Collection;
import java.util.List;
import org.csstudio.display.builder.model.WidgetDescriptor;
import org.csstudio.display.builder.model.spi.WidgetsService;

/**
 *
 * @author Daniel
 */
public class MapWidgetService implements WidgetsService {

    @Override
    public Collection<WidgetDescriptor> getWidgetDescriptors() {
        return List.of(new MapDescriptor());
    }
    
}
