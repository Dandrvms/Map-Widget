package com.ceos.display.model;

import java.util.Arrays;
import java.util.List;
import org.csstudio.display.builder.model.ArrayWidgetProperty;
import org.csstudio.display.builder.model.StructuredWidgetProperty;

import org.csstudio.display.builder.model.Widget;
import org.csstudio.display.builder.model.WidgetProperty;
import org.csstudio.display.builder.model.WidgetPropertyCategory;
import org.csstudio.display.builder.model.WidgetPropertyDescriptor;
import org.csstudio.display.builder.model.properties.CommonWidgetProperties;

import static org.csstudio.display.builder.model.properties.CommonWidgetProperties.propItemsFromPV;
import org.csstudio.display.builder.model.properties.EnumWidgetProperty;

import org.csstudio.display.builder.model.widgets.WritablePVWidget;

/**
 *
 * @author Daniel
 *
 * Defines the map props
 */
public class MapWidget extends WritablePVWidget {

    public static final String WIDGET_TYPE = "map";

//    private volatile PointsWidgetProperty coords;
    private ArrayWidgetProperty<StructuredWidgetProperty> coords;

    public static final StructuredWidgetProperty.Descriptor propMarker
            = new StructuredWidgetProperty.
                    Descriptor(WidgetPropertyCategory.MISC, "marker", "Marker");

    public static final WidgetPropertyDescriptor<MarkerIcon> propIconType
            = new WidgetPropertyDescriptor<MarkerIcon>(WidgetPropertyCategory.
                    MISC, "icon_type", "Icon Type") {
                        
        @Override
        public WidgetProperty<MarkerIcon> createProperty(final Widget widget, final MarkerIcon defaultValue) {
            return new EnumWidgetProperty<>(this, widget, defaultValue);
        }
    };

    public static final ArrayWidgetProperty.Descriptor<StructuredWidgetProperty> propCoords
            = new ArrayWidgetProperty.Descriptor<>(
                    WidgetPropertyCategory.MISC, "coords", "Markers",
                    (widget, index) -> propMarker.createProperty(widget, Arrays.asList(
                            CommonWidgetProperties.newDoublePropertyDescriptor(WidgetPropertyCategory.MISC, "lat", "Latitude").
                                    createProperty(widget, 0.0),
                            CommonWidgetProperties.newDoublePropertyDescriptor(WidgetPropertyCategory.MISC, "lon", "Longitude").
                                    createProperty(widget, 0.0),
                            CommonWidgetProperties.newStringPropertyDescriptor(WidgetPropertyCategory.MISC, "name", "Name").
                                    createProperty(widget, "Marker " + index),
                            CommonWidgetProperties.newFilenamePropertyDescriptor(WidgetPropertyCategory.MISC, "bob", "Display binding").
                                    createProperty(widget, ""),
                            propIconType.createProperty(widget, MarkerIcon.DEFAULT)
                    ))
            );


    private volatile WidgetProperty<Boolean> items_from_pv;

    public MapWidget() {
        super(WIDGET_TYPE);
    }

    @Override
    protected void defineProperties(final List<WidgetProperty<?>> properties) {
        super.defineProperties(properties);

        properties.add(items_from_pv = propItemsFromPV.createProperty(this, true));
        properties.add(coords = propCoords.createProperty(this, Arrays.asList()));

    }

    public ArrayWidgetProperty<StructuredWidgetProperty> propCoords() {
        return coords;
    }

    public WidgetProperty<Boolean> propItemsFromPV() {
        return items_from_pv;
    }
}
