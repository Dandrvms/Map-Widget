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

import com.gluonhq.attach.util.Platform;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MapNode extends StackPane {

    private static final Logger LOGGER = Logger.getLogger(MapNode.class.getName());

    static {

        Logger gluonLogger = Logger.getLogger("com.gluonhq");
        gluonLogger.setLevel(Level.SEVERE);

        try {
            if (MapNode.class.getResourceAsStream("/logging.properties") != null) {
                LogManager.getLogManager().readConfiguration(MapNode.class.getResourceAsStream("/logging.properties"));
            }
        } catch (IOException e) {

        }
    }

    private final MapView view;
    private final PoiLayer markerLayer;

    public MapNode() {
        this.setPickOnBounds(true);
        view = new MapView();
        markerLayer = new PoiLayer();
        view.addLayer(markerLayer);

        MapPoint venezuela = new MapPoint(7.0, -66.0);
        view.setZoom(6);
        view.setCenter(venezuela);

        view.prefWidthProperty().bind(this.widthProperty());
        view.prefHeightProperty().bind(this.heightProperty());

        this.getChildren().add(view);

        setupInteractions();
    }

    private void setupInteractions() {
        view.setOnContextMenuRequested(e -> {
            MapPoint point = view.getMapPosition(e.getX(), e.getY());
            if (point != null) {
                addMarker(point);
            }
        });
    }

    private void addMarker(MapPoint point) {
        Circle circle = new Circle(7, Color.RED);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);

        circle.setOnMouseClicked(e -> {
            if (e.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
//                view.flyTo(1.0, point, view.getZoom() + 20);
                view.setCenter(point);
                view.setZoom(15);
                e.consume();
            }
        });

        markerLayer.addPoint(point, circle);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (view != null) {
            view.resizeRelocate(0, 0, getWidth(), getHeight());
        }
    }

    private static class PoiLayer extends com.gluonhq.maps.MapLayer {

        private final javafx.collections.ObservableList<Pair<MapPoint, javafx.scene.Node>> points
                = javafx.collections.FXCollections.observableArrayList();

        public void addPoint(MapPoint p, javafx.scene.Node icon) {
            points.add(new Pair<>(p, icon));
            this.getChildren().add(icon);
            this.markDirty();
        }

        @Override
        protected void layoutLayer() {
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
