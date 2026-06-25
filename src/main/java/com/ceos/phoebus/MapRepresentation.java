package com.ceos.phoebus;

import com.ceos.map.ui.MapNode;
import com.ceos.map.ui.MarkerDialog;
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

        model.propWidth().addUntypedPropertyListener(contentChangedListener);

        model.propHeight().addUntypedPropertyListener(contentChangedListener);

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
        jfx_node.setPrefWidth(((Number) val).doubleValue());
        jfx_node.setPrefHeight(((Number) val).doubleValue());
    }

    private void addMarkerToModel(double lat, double lon, String display) throws Exception {
        StructuredWidgetProperty newMarker = model_widget.addMarker(lat, lon, display);
        for (WidgetProperty<?> p : newMarker.getValue()) {
            p.addUntypedPropertyListener(markerListener);
        }
    }

    @Override
    protected void unregisterListeners() {
        model_widget.propWidth().removePropertyListener(contentChangedListener);
        model_widget.propHeight().removePropertyListener(contentChangedListener);
        model_widget.propCoords().removePropertyListener(contentChangedListener);

        for (StructuredWidgetProperty marker : model_widget.propCoords().getValue()) {
            for (WidgetProperty<?> prop : marker.getValue()) {
                prop.removePropertyListener(markerListener);
            }
        }
        super.unregisterListeners();
    }
    
    @Override
    public void dispose(){
        jfx_node.dispose();
        super.dispose();
    }

}
