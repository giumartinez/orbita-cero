package puppy.code;

import java.util.Arrays;
import java.util.List;

public class FabricaEnemigosRonda implements FabricaEnemigos {
    private final List<FabricaEnemigos> secuencia;

    public FabricaEnemigosRonda(GestorRecursos recursos) {
        secuencia = Arrays.asList(
            (i, x, y, vx, vy) -> new NaveArtillera(x, y, vx, vy, recursos.getTexturaNaveArtillera()),
            (i, x, y, vx, vy) -> new NaveCaza(x, y, vx, vy, recursos.getTexturaNaveCaza()),
            (i, x, y, vx, vy) -> new NaveCaza(x, y, vx, vy, recursos.getTexturaNaveCaza())
        );
    }

    @Override
    public NaveEnemiga crear(int indice, int x, int y, int velocidadX, int velocidadY) {
        return secuencia.get(indice % secuencia.size()).crear(indice, x, y, velocidadX, velocidadY);
    }
}
