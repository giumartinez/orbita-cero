# GM-4: resolución del feedback de clase abstracta e interfaz

## Requerimiento y corrección

Las bases exigen una clase abstracta con al menos dos hijas y una interfaz implementada por al menos dos clases, ambas usadas en el programa. `NaveEnemiga` es el tipo común de `NaveCaza` y `NaveArtillera` en la lista y el ciclo de `PantallaJuego`; `Colisionable` es implementada por `NaveEnemiga`, `Nave4` y `Bullet`.

- `NaveEnemiga.checkCollision(NaveEnemiga)` consulta ahora `colisionaCon(otra)` antes de calcular la separación y el rebote. Así respeta el contrato compartido, incluido el rechazo de una referencia nula o una entidad destruida.
- El hook `alRecibirImpacto()` ya era opcional por la corrección de GM-8: `NaveCaza` no implementa un método vacío y `NaveArtillera` conserva su reacción especial. Se verificó y se preservó ese comportamiento.
- El apartado escrito de GM-4 en `docs/Reporte_GM.docx` explica por qué la clase base es abstracta y por qué `Colisionable.colisionaCon()` es `default`. Se trabajó con el texto y la evidencia, sin usar los diagramas del reporte como sustento.
- Tras la revisión de `RevisarGM4.md`, se comprobó que la copia de `Reporte_GM.docx` en la raíz del proyecto no incluía esas justificaciones. Se incorporaron en ambas copias sin trasladar cambios de otros GM entre ellas. También se corrigió la frase que atribuía la creación directa de enemigos al constructor de `PantallaJuego` y se precisaron las referencias de código del apartado GM-4 conforme al commit de esta resolución.

## Justificación de diseño

La clase abstracta posee estado privado y lógica común de movimiento, vida, disparo y choque. Una interfaz no puede compartir las invariantes de ese estado ni reutilizar directamente su implementación; forzaría duplicación o una delegación adicional en cada nave. La clase abstracta también impide instanciar un enemigo sin tipo concreto y exige a cada hija solo nombre y recompensa. El hook opcional mantiene el algoritmo de impacto en la base y permite añadir reacciones particulares sin obligar a las hijas que no las necesitan.

La interfaz mantiene un contrato pequeño para entidades de jerarquías distintas. Su método `default` implementa una regla idéntica de detección de contacto, mientras cada objeto decide el efecto del impacto. El filtro inicial de `checkCollision` usa esa regla; la separación y el cambio de velocidad permanecen encapsulados en `NaveEnemiga`. Las hijas conservan el mismo contrato y pueden sustituirse en la partida. No se introdujo una dependencia de la clase base hacia una hija concreta.

## Evidencia y límites

- `NaveEnemigaColisionableTest` comprueba que una referencia nula y una nave destruida no cambian las velocidades durante un choque, y que dos naves activas superpuestas se separan.
- `NaveEnemigaImpactoTest` comprueba el hook opcional y la reacción de la artillera; `NaveEnemigaReboteTest` comprueba la inversión de velocidad.
- `./gradlew.bat :core:test :core:compileJava :lwjgl3:compileJava`: **BUILD SUCCESSFUL**.
- La interacción manual jugando queda pendiente de la comprobación del usuario. La compilación y las pruebas automáticas no la sustituyen.
- La revisión posterior no detectó una nueva falla de código; los cambios posteriores fueron exclusivamente documentales. Las referencias de líneas describen el código del commit GM-4 y pueden desplazarse con cambios futuros.
