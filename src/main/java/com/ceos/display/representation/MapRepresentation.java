package com.ceos.display.representation;

import com.ceos.display.model.MapWidget;
import com.ceos.display.model.MarkerData;
import com.ceos.display.model.MarkerIcon;
import com.ceos.display.ui.MapNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import org.csstudio.display.builder.model.StructuredWidgetProperty;
import org.csstudio.display.builder.model.UntypedWidgetPropertyListener;
import org.csstudio.display.builder.model.WidgetProperty;
import org.csstudio.display.builder.model.WidgetPropertyCategory;
import org.csstudio.display.builder.model.properties.CommonWidgetProperties;
import org.csstudio.display.builder.representation.javafx.widgets.JFXBaseRepresentation;

/**
 *
 * @author Daniel
 *
 * Returns the actual javafx node for phoebus
 *
 */
public class MapRepresentation extends JFXBaseRepresentation<MapNode, MapWidget> {

    private final UntypedWidgetPropertyListener contentChangedListener = this::contentChanged;
    private final UntypedWidgetPropertyListener markerListener = (prop, old ,val) -> setupMarkers(); 
    private volatile List<String> coords = Collections.emptyList();
    private volatile int index = -1;

    @Override
    protected MapNode createJFXNode() throws Exception {
        return new MapNode(model_widget);
    }

    @Override
    public void updateChanges() {
        super.updateChanges();
        final MapWidget model = model_widget;
        final MapNode node = jfx_node;
        node.setPrefSize(model.propWidth().getValue(), model.propHeight().getValue());
        setupMarkers();

    }

    @Override
    public void registerListeners() {
        super.registerListeners();
        final MapWidget model = model_widget;
        final MapNode node = jfx_node;

        model.propWidth().addUntypedPropertyListener((prop, old, val) -> {
            node.setPrefWidth(((Number) val).doubleValue());
        });

        model.propHeight().addUntypedPropertyListener((prop, old, val) -> {
            node.setPrefHeight(((Number) val).doubleValue());
        });

        node.setPrefSize(model.propWidth().getValue().doubleValue(), model.propHeight().getValue().doubleValue());

        model.propCoords().addUntypedPropertyListener(contentChangedListener);
        attachListeners();

        boolean isEditMode = toolkit.isEditMode();
        node.setEditMode(isEditMode);
        if (isEditMode) {
            node.setOnAddMarker((lat, lon) ->
                Platform.runLater(() -> addMarkerToModel(lat, lon))
            );
        }
    }

    private void setupMarkers() {
//        Points points = model_widget.propCoords().getValue();
        List<StructuredWidgetProperty> markers = model_widget.propCoords().getValue();

        List<MarkerData> markerList = new ArrayList<>();

        for (StructuredWidgetProperty marker : markers) {
            List<WidgetProperty<?>> props = marker.getValue();

            double lat = (Double) props.get(0).getValue();
            double lon = (Double) props.get(1).getValue();
            String name = (String) props.get(2).getValue();
            String display = (String) props.get(3).getValue();
            MarkerIcon icon = (MarkerIcon) props.get(4).getValue();
            
            markerList.add(new MarkerData(lat, lon, name, display, icon));
        }

        jfx_node.setMarkers(markerList);

        Platform.runLater(() -> {
            jfx_node.setMarkers(markerList);
        });
    }
    
    private void attachListeners() {
            for (StructuredWidgetProperty marker : model_widget.propCoords().getValue()) {
                for (WidgetProperty<?> prop : marker.getValue()) {

                    prop.addUntypedPropertyListener(markerListener);
                }
            }
        }

    private void contentChanged(final WidgetProperty<?> prop, final Object old, Object val) {
        setupMarkers();
        attachListeners();
    }

    private void addMarkerToModel(double lat, double lon) {
        MapWidget model = model_widget;
        List<StructuredWidgetProperty> currentList = new ArrayList<>(model.propCoords().getValue());

        StructuredWidgetProperty newMarker = MapWidget.propMarker.createProperty(model, Arrays.asList(
                CommonWidgetProperties.newDoublePropertyDescriptor(WidgetPropertyCategory.MISC, "lat", "Latitude")
                        .createProperty(model, lat),
                CommonWidgetProperties.newDoublePropertyDescriptor(WidgetPropertyCategory.MISC, "lon", "Longitude")
                        .createProperty(model, lon),
                CommonWidgetProperties.newStringPropertyDescriptor(WidgetPropertyCategory.MISC, "name", "Name")
                        .createProperty(model, "Marker " + currentList.size()),
                CommonWidgetProperties.newFilenamePropertyDescriptor(WidgetPropertyCategory.MISC, "bob", "Display binding")
                        .createProperty(model, ""),
                MapWidget.propIconType.createProperty(model, MarkerIcon.DEFAULT)
        ));

        currentList.add(newMarker);
        model.propCoords().setValue(currentList);
    }

}
