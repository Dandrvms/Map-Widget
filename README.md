# Map Widget for Phoebus

This widget provides an interactive map for the [Phoebus Control System Studio](https://github.com/ControlSystemStudio/phoebus), allowing users to place markers and open related displays directly from the map interface.

## Prerequisites

- **Java JDK 21** (must match your Phoebus installation version)
- **Apache Maven 3.9+**
- **Phoebus 5.0.5+**
- **Map tile server** (see below)

## Map Tile Server Requirements

This widget requires a map tile server to function. 

You can run a local tile server using [Switch2OSM](https://switch2osm.org/) or a similar solution. The default configuration points to a local server:
```java
private static final String host = "http://172.28.41.114/hot/";
```

Tile URL format: `{host}/{zoom}/{x}/{y}.png`

## Build Instructions

The widget uses the `maven-shade-plugin` to package all external dependencies (Gluon Maps, Attach libraries) into a single "fat JAR":

```bash
mvn clean package
```

The resulting JAR will be located at:
```
target/Map-Widget-0.13.0-SNAPSHOT.jar
```

## Adding to Phoebus

This widget is designed as a plugin for Phoebus. For detailed deployment instructions, including custom launch scripts and classpath configuration, **refer to the [hello-widget README](https://github.com/dandrvms/hello-widget/blob/phoebus/README.md#deployment--integration)** – the same principle applies: use a custom launcher script to include the widget JAR alongside the Phoebus product JAR.

## Features

- Display interactive maps powered by [Gluon Maps](https://github.com/gluonhq/maps)
- Add, remove, and configure markers dynamically
- Each marker can be linked to a Phoebus display (`.bob` file) for quick navigation

## How It Works

The widget integrates the Gluon Maps library into the Phoebus display framework:

1. **Map Rendering**: Uses `MapView` from Gluon Maps to render map tiles and handle pan/zoom interactions.
2. **Markers**: Markers are stored as structured properties in the widget model (`MapWidget.propCoords`). Each marker contains:
   - Latitude and longitude
   - Display name
   - Path to a Phoebus display (`.bob` file)
   - Icon type (enum)
3. **Tile Retrieval**: By default, the widget uses a local tile server (`LocalTileRetriever`), but can be configured to use any tile source.
4. **Interaction**: Clicking a marker zooms to its location; double-clicking opens the associated display.

---

*Based on the [hello-widget](https://github.com/dandrvms/hello-widget) template for Phoebus custom widgets.*