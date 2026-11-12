# GM-5: encapsulamiento y principios de orientación a objetos

## Cambios realizados

- `NaveEnemiga.update(...)` devuelve los proyectiles generados. `PantallaJuego` los incorpora a su lista privada; ya no entrega esa lista al enemigo ni permite que este la modifique.
- Cada `NaveEnemiga` aplica la separación, el cambio de velocidad y la sincronización de su propio `Sprite` mediante `aplicarChoque(...)`. La colisión conserva el cálculo de `ColisionAsteroides` y los límites del área de juego, sin escribir atributos de otra instancia.
- `Nave4` recibe `EstrategiaDisparo` en el constructor y publica balas mediante `ReceptorBalas` en `update(...)`. Así deja de construir `DisparoSimple` y de depender de `PantallaJuego`.
- `PantallaJuego` recibe `FabricaEnemigos`; `FabricaEnemigosRonda` conserva la secuencia original de una artillera por dos cazas. La elección de tipos ya no está fijada en el bucle de la pantalla.
- `PantallaJuego` invoca `Bullet.procesarColision(...)` sin consultar `esOrigenJugador()`. `BalaEnemiga` especializa el tratamiento del impacto contra el jugador; las estrategias crean el tipo de bala correspondiente.
- Se corrigió únicamente la sección GM-5 de `Reporte_GM.docx` para describir el código actual y sus límites de DIP.
- Tras revisar `RevisarGM5.md`, `PantallaJuego` entrega a las balas una vista no modificable de `enemigos`; conserva la capacidad exclusiva de agregar o quitar enemigos. `Nave4` pide `rebotarContraJugador()` al enemigo, que invierte sus propias velocidades.

## Justificación

La pantalla conserva la propiedad de sus colecciones y coordina la ronda. Cada enemigo controla sus invariantes de posición, velocidad y sprite. `ReceptorBalas` y `FabricaEnemigos` invierten las dependencias concretas señaladas. La resolución de impactos por polimorfismo y la fábrica permiten extender tipos sin añadir condicionales por origen o por tipo de enemigo en `PantallaJuego`. `NaveCaza` y `NaveArtillera` mantienen el contrato de `NaveEnemiga`, y `BalaEnemiga` mantiene el de `Bullet`.

La vista no modificable permite conservar el recorrido polimórfico en `Bullet` sin cederle la administración de la colección. La operación de rebote mantiene en `NaveEnemiga` la responsabilidad por sus velocidades y conserva la inversión de ambos ejes. El Builder sigue configurando la fábrica predeterminada en un solo punto; `PantallaJuego` acepta cualquier implementación de `FabricaEnemigos`. No se necesita ampliar el Builder para resolver el feedback GM-5.

## Verificación

- `./gradlew.bat :core:test :core:compileJava :lwjgl3:compileJava`: **BUILD SUCCESSFUL**.
- Revisión independiente inicial de código y pruebas automáticas: detectó una lectura directa de la velocidad de otra nave; se corrigió con sus accesores antes de cerrar el cambio original.
- Se agregó una prueba automática para verificar que `rebotarContraJugador()` invierte ambas velocidades. La vista no modificable se verifica por construcción mediante `Collections.unmodifiableList(...)`; la interacción completa de colisiones permanece pendiente de prueba manual.
- Prueba manual de juego: pendiente de ejecución por el usuario. La compilación y los tests no acreditan interacción manual.

No se modificaron documentos de requerimientos ni bitácoras.
