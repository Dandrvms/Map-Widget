# How to Use the Map Widget

## Overview

This widget displays an interactive map where you can place custom markers. Each marker can optionally be linked to a Phoebus screen (`.bob`), which opens when you interact with it.

## Initial Setup

When you first instantiate the widget, it will appear empty, since it needs to point to a map tile server, either online or offline.

**Steps:**
1. Drag the widget onto the editor.
2. In the properties palette, fill in the `Tile server url` field with the address of your tile server:
   - **Offline server:** follow the instructions at [switch2osm.org](https://switch2osm.org) to set up your own server.
   - **Online server:** use `https://tile.openstreetmap.org/`, OpenStreetMap's public server (requires an internet connection).
3. Once the URL is set, scroll over the widget so it starts loading and displaying the map.

> 💡 **Tip:** If the map doesn't show up, double-check that the server URL is correct and, if using the online server, that you have an internet connection.

## Interactions

| Action | Result |
|---|---|
| Click and hold + move the mouse | Pans the map |
| Scroll | Zooms in / out |
| Right-click on the map | Opens a context menu to add a marker |
| Right-click on a marker | Opens a context menu to edit or delete the marker |
| Click and hold on a marker + move | Moves the marker, updating its coordinates |
| Double-click on a marker | Opens the associated screen (if any) |

### Adding a marker
1. Right-click on the point of the map where you want to place it.
2. Select **Add** from the context menu.
3. In the dialog that opens, you can:
   - Give the marker a **name**.
   - Select the **screen** that will open when you double-click it.
4. Once confirmed, the marker will be created at the coordinates where you clicked.

### Editing or deleting a marker
Right-click on the marker and choose the corresponding option from the context menu.

### Opening the associated screen
Double-click on the marker. If it has an associated `.bob` screen, it will open; otherwise, an error will be shown.

> ⚠️ **Important:** The widget captures all clicks to handle map interactions, so it **does not respond** to Phoebus's normal clicks for resizing or moving the widget itself. To move or resize the widget, select it from the **widget tree**, or box-select it with the selector without clicking directly on the map.

## Properties

In the Phoebus properties palette you'll find:

- **Tile server url:** the address of the tile server.
- **Markers (list):** lets you add new markers manually and view or edit all existing markers. Each marker has the following properties:

| Property | Description |
|---|---|
| Latitude | The marker's latitude coordinate |
| Longitude | The marker's longitude coordinate |
| Name | The marker's visible name |
| Associated screen | The `.bob` screen opened on double-click |
| Icon | The icon used to represent the marker |

All of these properties can be edited manually from the palette.

> 📝 **Note:** If you create a marker directly from the properties palette (instead of from the map), it will default to coordinates `(0, 0)`. You'll need to edit the latitude and longitude manually to position it correctly.
