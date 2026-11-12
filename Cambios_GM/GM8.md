# GM-8: resolución del feedback de Template Method

## Problema y contexto

`NaveEnemiga.recibirImpacto(int)` validaba y descontaba vida, pero podía ser sobrescrito y solo delegaba en un método abstracto `alRecibirImpacto()`. Esto obligaba a `NaveCaza` a implementar un método vacío. El feedback señaló que la secuencia del patrón quedaba débil y no protegía el algoritmo común.

## Cambios realizados

- `recibirImpacto(int)` es `final` y ordena cuatro pasos: validar el impacto, aplicar el daño, ejecutar la reacción opcional y devolver si la nave quedó destruida.
- `puedeRecibirImpacto(int)` y `aplicarDano(int)` son privados. El primero rechaza daño no positivo y naves ya destruidas; el segundo mantiene `puntosVida` en cero o más.
- `alRecibirImpacto()` ahora es un hook protegido con implementación vacía por defecto. `NaveCaza` ya no tiene un override sin efecto.
- `NaveArtillera` conserva su reacción especial: cuando queda con una vida reduce a la mitad el intervalo de disparo y cambia de color.
- Se actualizó la explicación escrita de GM-8 en `Reporte_GM.docx` y dos referencias textuales previas que afirmaban que el hook era abstracto. Los diagramas y enlaces UML se dejaron fuera de esta corrección, conforme a la solicitud.
- Se añadió `NaveEnemigaImpactoTest` para comprobar daño inválido, destrucción, rechazo de impactos posteriores y la aceleración única de la artillera.

## Justificación de diseño

La clase base conserva la responsabilidad de la secuencia y el estado de vida; las subclases solo pueden variar la reacción. Los pasos privados ocultan la implementación e impiden que una extensión altere las invariantes. El método `final` preserva el contrato de retorno usado por `PantallaJuego`. El hook opcional permite añadir nuevos enemigos con o sin reacción adicional sin modificar el algoritmo común (abierto-cerrado). `NaveCaza` y `NaveArtillera` siguen siendo sustituibles como `NaveEnemiga`: un impacto válido descuenta vida y el resultado indica destrucción. Esta modificación no introduce una dependencia nueva de la clase base hacia implementaciones concretas.

## Evidencia y límites

- `./gradlew.bat :core:test :core:compileJava :lwjgl3:compileJava`: **BUILD SUCCESSFUL**. Incluye los casos de `NaveEnemigaImpactoTest`.
- Revisión independiente de código y pruebas automáticas: confirmó la secuencia final, el hook opcional, la ausencia del override vacío y la conservación de la reacción especial.
- Arranque e interacción manual del juego: **pendientes de comprobación por el usuario**. La compilación y los tests no acreditan jugabilidad manual.
