package com.ceos.phoebus;

import com.ceos.map.model.MarkerData;
import com.ceos.map.model.MarkerIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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


    //***********************************
    //Marker structured properties order
    public static final int IDX_LAT = 0;
    public static final int IDX_LON = 1;
    public static final int IDX_NAME = 2;
    public static final int IDX_BOB = 3;
    public static final int IDX_ICON = 4;
    //************************************

    public static final String WIDGET_TYPE = "map";

    private ArrayWidgetProperty<StructuredWidgetProperty> coords;

    public static final StructuredWidgetProperty.Descriptor propMarker
            = new StructuredWidgetProperty.Descriptor(WidgetPropertyCategory.MISC, "marker", "Marker");

    public static final WidgetPropertyDescriptor<MarkerIcon> propIconType
            = new WidgetPropertyDescriptor<MarkerIcon>(WidgetPropertyCategory.MISC, "icon_type", "Icon Type") {

        @Override
        public WidgetProperty<MarkerIcon> createProperty(final Widget widget, final MarkerIcon defaultValue) {
            return new EnumWidgetProperty<>(this, widget, defaultValue);
        }
    };

    public static final ArrayWidgetProperty.Descriptor<StructuredWidgetProperty> propCoords
            = new ArrayWidgetProperty.Descriptor<>(
                    WidgetPropertyCategory.MISC, "coords", "Markers",
                    (widget, index) -> propMarker.createProperty(widget,
                            markerProperties(widget, 0.0, 0.0, "Marker " + index, "", MarkerIcon.DEFAULT)
                            ), 0
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

    public StructuredWidgetProperty addMarker(double lat, double lon, String display, String name) throws Exception {
        StructuredWidgetProperty newMarker = propMarker.createProperty(this,
                markerProperties(this, lat, lon, name, display, MarkerIcon.DEFAULT));

        coords.addElement(newMarker);
        return newMarker;
    }


    private static List<WidgetProperty<?>> markerProperties(Widget widget,
                                                            double lat, double lon, String name, String display, MarkerIcon icon) {
        return Arrays.asList(
                CommonWidgetProperties.newDoublePropertyDescriptor(WidgetPropertyCategory.MISC, "lat", "Latitude")
                        .createProperty(widget, lat),
                CommonWidgetProperties.newDoublePropertyDescriptor(WidgetPropertyCategory.MISC, "lon", "Longitude")
                        .createProperty(widget, lon),
                CommonWidgetProperties.newStringPropertyDescriptor(WidgetPropertyCategory.MISC, "name", "Name")
                        .createProperty(widget, name),
                CommonWidgetProperties.newFilenamePropertyDescriptor(WidgetPropertyCategory.MISC, "bob", "Display binding")
                        .createProperty(widget, display),
                propIconType.createProperty(widget, icon)
        );
    }

    public List<MarkerData> getMarkers() {
        List<MarkerData> result = new ArrayList<>();
        for (StructuredWidgetProperty marker : coords.getValue()) {
            List<WidgetProperty<?>> p = marker.getValue();
            result.add(new MarkerData(
                    (Double) p.get(IDX_LAT).getValue(),
                    (Double) p.get(IDX_LON).getValue(),
                    (String) p.get(IDX_NAME).getValue(),
                    (String) p.get(IDX_BOB).getValue(),
                    (MarkerIcon) p.get(IDX_ICON).getValue()
            ));
        }
        return result;
    }

    public MarkerData getMarker(int index) throws Exception {
        List<StructuredWidgetProperty> markers = new ArrayList<>(coords.getValue());
        List<WidgetProperty<?>> p = markers.get(index).getValue();
        MarkerData marker = new MarkerData(
                (Double) p.get(IDX_LAT).getValue(),
                (Double) p.get(IDX_LON).getValue(),
                (String) p.get(IDX_NAME).getValue(),
                (String) p.get(IDX_BOB).getValue(),
                (MarkerIcon) p.get(IDX_ICON).getValue()
        );
        return marker;
    }
    
    public void removeMarker(int index) throws Exception {
        List<StructuredWidgetProperty> current = new ArrayList<>(coords.getValue());
        if (index < 0 || index >= current.size()) return;
        current.remove(index);
        coords.setValue(current);
    }

    public void updateMarker(int index, String name, String display, MarkerIcon icon) throws Exception {
        List<WidgetProperty<?>> props = coords.getValue().get(index).getValue();
        ((WidgetProperty<String>) props.get(IDX_NAME)).setValue(name);
        ((WidgetProperty<String>) props.get(IDX_BOB)).setValue(display);
        ((WidgetProperty<MarkerIcon>) props.get(IDX_ICON)).setValue(icon);
    }

    public void updateMarkerPosition(int index, MarkerData marker) throws Exception {
        List<WidgetProperty<?>> props = coords.getValue().get(index).getValue();
        ((WidgetProperty<Double>) props.get(IDX_LAT)).setValue(marker.getPoint().getLatitude());
        ((WidgetProperty<Double>) props.get(IDX_LON)).setValue(marker.getPoint().getLongitude());
    }
}
