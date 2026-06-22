/*
 * Copyright (c) 2018, 2023, Gluon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ceos.display.ui;

import com.ceos.display.model.MarkerData;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.scene.layout.StackPane;

import java.util.logging.Level;

import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import org.csstudio.display.builder.model.Widget;

/**
 *
 * @author Daniel
 *
 * JavaFX Node that contains the map
 *
 */
public class MapNode extends StackPane {

    private final int maxZoom;
    private Widget widget;

    private static final Logger LOGGER = Logger.getLogger(MapNode.class.getName());

    static {

        String[] loggers = {"com.gluonhq", "com.gluonhq.maps", "com.gluonhq.impl.maps"};
        for (String l : loggers) {
            Logger logger = Logger.getLogger(l);
            logger.setLevel(Level.OFF);
            for (java.util.logging.Handler handler : Logger.getLogger("").getHandlers()) {
                if (handler instanceof java.util.logging.ConsoleHandler) {
                    // handler.setLevel(Level.WARNING); 
                }
            }
        }
    }

    private boolean editMode = false;
    private BiConsumer<Double, Double> onAddMarker;

    private final MapView view;
    private final PoiLayer markerLayer;

    public MapNode() {
        this.maxZoom = 15;
        this.setPickOnBounds(true);
        this.setPrefSize(400, 400);

        view = new MapView();
        markerLayer = new PoiLayer();
        view.addLayer(markerLayer);

        MapPoint country = new MapPoint(7.0, -66.0);
        view.setZoom(6);
        view.setCenter(country);

        view.prefWidthProperty().bind(this.widthProperty());
        view.prefHeightProperty().bind(this.heightProperty());

        this.getChildren().add(view);

//        setupInteractions();
    }

    public MapNode(Widget widget) {
        this.widget = widget;
        this.maxZoom = 15;
        this.setPickOnBounds(true);
        this.setPrefSize(400, 400);

        view = new MapView();
        markerLayer = new PoiLayer();
        view.addLayer(markerLayer);

        MapPoint country = new MapPoint(7.0, -66.0);
        view.setZoom(6);
        view.setCenter(country);

        view.prefWidthProperty().bind(this.widthProperty());
        view.prefHeightProperty().bind(this.heightProperty());

        this.getChildren().add(view);

//        setupInteractions();
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            setupEditModeContextMenu();
        } else {
            view.setOnContextMenuRequested(null);
        }
    }

    public void setOnAddMarker(BiConsumer<Double, Double> callback) {
        this.onAddMarker = callback;
    }

    private void setupEditModeContextMenu() {
        view.setOnContextMenuRequested(e -> {
            if (!editMode || onAddMarker == null) return;
            e.consume();
            MapPoint point = view.getMapPosition(e.getX(), e.getY());
            if (point == null) return;

            ContextMenu cm = new ContextMenu();
            MenuItem addItem = new MenuItem("Add Marker Here");
            addItem.setOnAction(ev ->
                onAddMarker.accept(point.getLatitude(), point.getLongitude())
            );
            cm.getItems().add(addItem);
            cm.show(view, e.getScreenX(), e.getScreenY());
        });
    }

    public void setMarkers(List<MarkerData> markers) {
        markerLayer.clearChildren();
        for (MarkerData marker : markers) {
            addMarker(marker);
        }
    }

    private void addMarker(MarkerData point) {
        MapMarker marker = new MapMarker(point.getIconType());
        marker.setDisplay(point.getDisplayPath());

        marker.setOnMouseClicked(e -> {

            if (e.getButton() == MouseButton.PRIMARY) {

                if (e.getClickCount() == 2) {
                    marker.openDisplay(this.widget);
                } else {
                    Platform.runLater(() -> {
                        view.setCenter(point.getPoint());
                        if (view.getZoom() >= maxZoom) {
                            view.setZoom(maxZoom - 0.0001);
                        }
                        view.setZoom(maxZoom);
                        markerLayer.markdirty();
                    });
                }

                e.consume();
            }
        });

        Tooltip data = new Tooltip(point.getName() + ": " + point.getPoint().getLatitude() + ", " + point.getPoint().getLongitude());
        Tooltip.install(marker, data);

        markerLayer.addPoint(point.getPoint(), marker);
    }

    private static class PoiLayer extends com.gluonhq.maps.MapLayer {

        private final javafx.collections.ObservableList<Pair<MapPoint, javafx.scene.Node>> points
                = javafx.collections.FXCollections.observableArrayList();

        public void addPoint(MapPoint p, javafx.scene.Node icon) {
            points.add(new Pair<>(p, icon));
            this.getChildren().add(icon);
            this.markDirty();
        }

        public void clearChildren() {
            this.getChildren().clear();
            points.clear();
        }
        
        public void markdirty(){
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
            for (Pair<MapPoint, javafx.scene.Node> candidate : points) {
                MapPoint point = candidate.getKey();
                javafx.scene.Node icon = candidate.getValue();
                javafx.geometry.Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
                if (mapPoint != null) {
                    icon.setVisible(true);
                    icon.setTranslateX(mapPoint.getX());
                    icon.setTranslateY(mapPoint.getY());
                }
            }
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
