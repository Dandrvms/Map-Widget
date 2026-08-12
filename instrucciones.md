# Cómo usar el Map Widget

## Descripción general

Este widget muestra un mapa interactivo en el que se pueden colocar marcadores personalizados. Cada marcador puede vincularse opcionalmente a una pantalla de Phoebus (`.bob`), que se abrirá al interactuar con él.

## Configuración inicial

Al instanciar el widget por primera vez, aparecerá vacío, ya que necesita apuntar a un servidor de mapas (*tile server*), ya sea online u offline.

**Pasos:**
1. Arrastra el widget al editor.
2. En la paleta de propiedades, completa el campo `Tile server url` con la dirección del servidor de mapas:
   - **Servidor offline:** sigue las instrucciones de [switch2osm.org](https://switch2osm.org) para configurar tu propio servidor.
   - **Servidor online:** usa `https://tile.openstreetmap.org/`, el servidor público de OpenStreetMap (requiere conexión a internet).
3. Una vez configurada la URL, haz scroll sobre el widget para que comience a cargar y mostrar el mapa.

> 💡 **Tip:** Si el mapa no se muestra, revisa que la URL del servidor sea correcta y que tengas conexión a internet (en el caso del servidor online).

## Interacciones

| Acción | Resultado |
|---|---|
| Clic sostenido + mover el ratón | Desplaza el mapa (*pan*) |
| Scroll | Zoom in / Zoom out |
| Clic derecho sobre el mapa | Abre un menú contextual para agregar un marcador |
| Clic derecho sobre un marcador | Abre un menú contextual para editar o borrar el marcador |
| Clic sostenido sobre un marcador + mover | Mueve el marcador, actualizando sus coordenadas |
| Doble clic sobre un marcador | Abre la pantalla asociada (si tiene una) |

### Agregar un marcador
1. Haz clic derecho en el punto del mapa donde quieras ubicarlo.
2. Selecciona **Agregar** en el menú contextual.
3. En el diálogo que se abre, podrás:
   - Asignar un **nombre** al marcador.
   - Seleccionar la **pantalla** que se abrirá al hacer doble clic sobre él.
4. Al aceptar, el marcador se creará en las coordenadas donde hiciste clic.

### Editar o borrar un marcador
Haz clic derecho sobre el marcador y selecciona la opción correspondiente en el menú contextual.

### Abrir la pantalla asociada
Haz doble clic sobre el marcador. Si tiene una pantalla `.bob` asociada, se abrirá; de lo contrario, se mostrará un error.

> ⚠️ **Importante:** El widget captura todos los clics para gestionar las interacciones del mapa, por lo que **no responde** a los clics normales de Phoebus para redimensionar o desplazar el widget en sí. Para mover o redimensionar el widget, selecciónalo desde el **árbol de widgets**, o enmárcalo con el selector sin hacer clic directamente sobre el mapa.

## Propiedades

En la paleta de propiedades de Phoebus encontrarás:

- **Tile server url:** dirección del servidor de mapas.
- **Marcadores (lista):** permite agregar nuevos marcadores manualmente y visualizar o editar todos los marcadores existentes. Cada marcador tiene las siguientes propiedades:

| Propiedad | Descripción |
|---|---|
| Latitud | Coordenada de latitud del marcador |
| Longitud | Coordenada de longitud del marcador |
| Nombre | Nombre visible del marcador |
| Pantalla asociada | Pantalla `.bob` que se abre al hacer doble clic |
| Icono | Ícono utilizado para representar el marcador |

Todas estas propiedades pueden modificarse manualmente desde la paleta.

> 📝 **Nota:** Si creas un marcador directamente desde la paleta de propiedades (en lugar de hacerlo desde el mapa), se ubicará por defecto en las coordenadas `(0, 0)`. Deberás editar la latitud y la longitud manualmente para posicionarlo correctamente.
