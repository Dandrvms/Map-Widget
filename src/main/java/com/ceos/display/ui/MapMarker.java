package com.ceos.display.ui;


import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.csstudio.display.builder.model.Widget;
import org.csstudio.display.builder.runtime.script.ScriptUtil;

/**
 *
 * @author Daniel
 * 
 * Location Marker
 * 
 */
public class MapMarker extends Circle {

    private String display = "";
    private Map<String, String> macros = new HashMap<>();

    public MapMarker() {
        super(7, Color.RED);
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(2);

    }

    public void openDisplay(Widget widget) {
        try {
            ScriptUtil.openDisplay(widget, display, "TAB", macros);
        } catch (Exception e) {
            System.out.println("No se pudo abrir la pantalla " + display + "\n" + e);
        }
    }
    
    public void setDisplay(String path){
        this.display = path;
    }
    
    public String getDisplay(){
        return this.display;
    }
    
    public void setMacros(Map<String, String> macros){
        this.macros = macros;
    }
    
    
    public Map<String, String> getMacros(){
        return this.macros;
    }

}
