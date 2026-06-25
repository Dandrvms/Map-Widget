package com.ceos.map.model;

import com.gluonhq.maps.MapPoint;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 *
 * @author Daniel
 */
public class PoiLayer extends com.gluonhq.maps.MapLayer {

    private final javafx.collections.ObservableList<Pair<MapPoint, javafx.scene.Node>> points
            = javafx.collections.FXCollections.observableArrayList();

    private InvalidationListener zoomListener;

    @Override
    protected void initialize() {
        super.initialize();
        zoomListener = obs -> Platform.runLater(this::markDirty);
        baseMap.zoom().addListener(zoomListener);
    }

    public void addPoint(MapPoint p, javafx.scene.Node icon) {
        points.add(new Pair<>(p, icon));
        this.getChildren().add(icon);
        this.markDirty();
    }

    public void clearChildren() {
        this.getChildren().clear();
        points.clear();
    }

    public void markdirty() {
        this.markDirty();
    }

    public void removePoint(MapPoint p, javafx.scene.Node icon) {
        points.remove(new Pair<>(p, icon));
        this.getChildren().remove(icon);

    }

    @Override
    protected void layoutLayer() {
        if (points.isEmpty()) {
            return;
        }
        for (Pair<MapPoint, Node> candidate : points) {
            MapPoint point = candidate.getKey();
            Node icon = candidate.getValue();
            Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
            if (mapPoint != null) {
                icon.setVisible(true);
                icon.setTranslateX(mapPoint.getX() - 16);
                icon.setTranslateY(mapPoint.getY() - 32);
            }
        }
    }

    public void cleanup() {
        if (zoomListener != null) {
            baseMap.zoom().removeListener(zoomListener);
            zoomListener = null;
        }
    }

    private static class Pair<K, V> {

        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

}
