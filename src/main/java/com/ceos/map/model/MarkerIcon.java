package com.ceos.map.model;

/**
 *
 * @author Daniel
 * 
 * Icon types for markers
 */
public enum MarkerIcon {
    DEFAULT("default"),
    ZOOM("Zoom"),
    DRILL("Taladro"),
    PUMP("Bomba");
    
    private final String label;
    
    MarkerIcon(String label){
        this.label = label;
    }
    
    @Override
    public String toString(){
        return label;
    }
}

