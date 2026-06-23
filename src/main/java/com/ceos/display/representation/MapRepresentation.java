package com.ceos.display.representation;

import com.ceos.display.model.MapWidget;
import com.ceos.map.model.MarkerData;
import com.ceos.map.model.MarkerIcon;
import com.ceos.map.ui.MapNode;
import com.ceos.map.ui.MarkerDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import org.csstudio.display.builder.model.StructuredWidgetProperty;
import org.csstudio.display.builder.model.UntypedWidgetPropertyListener;
import org.csstudio.display.builder.model.WidgetProperty;
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
    private final UntypedWidgetPropertyListener markerListener = (prop, old, val) -> setupMarkers();
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
            node.setOnAddMarker((lat, lon)
                    -> Platform.runLater(() -> {
                        try {
                            MarkerDialog d = new MarkerDialog(lat, lon);
                            Optional<Boolean> result = d.showAndWait();
                            if (result.isPresent() && result.get()) {
                                addMarkerToModel(lat, lon, d.getDisplay());
                            }
                            
//                            addMarkerToModel(lat, lon, d.getDisplay());
                        } catch (Exception ex) {
                            System.getLogger(MapRepresentation.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        }
                    })
            );

            node.setOnDeleteMarker(index
                    -> Platform.runLater(() -> {
                        try {
                            model_widget.removeMarker(index);
                        } catch (Exception ex) {
                            System.getLogger(MapRepresentation.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        }
                    })
            );
        }
    }

    private void setupMarkers() {
        jfx_node.setMarkers(model_widget.getMarkers());
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
//        attachListeners();
    }

    private void addMarkerToModel(double lat, double lon, String display) throws Exception {
        StructuredWidgetProperty newMarker = model_widget.addMarker(lat, lon, display);
        for (WidgetProperty<?> p : newMarker.getValue()) {
            p.addUntypedPropertyListener(markerListener);
        }
    }

}
