package com.ceos.map.model;

import com.gluonhq.maps.MapPoint;

/**
 *
 * @author Daniel
 * 
 * DTO for markers
 */
public class MarkerData {
        private final MapPoint point; 
        private final String name;
        private final MarkerIcon icon;
        private final String displayPath;
        public MarkerData(double lat, double lon, String name, String displayPath, MarkerIcon icon){
            this.point = new MapPoint(lat, lon);
            this.name = name;
            this.icon = icon;
            this.displayPath = displayPath;
        }

        public MapPoint getPoint() { return point; }
        public String getName() { return name; }
        public MarkerIcon getIconType() { return icon; }
        public String getDisplayPath() { return displayPath; }
}
