package puppy.code;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RondaConstruidaTest {
    @Test public void copiaLosEnemigosAlRecibirYEntregarElEstado() {
        List<NaveEnemiga> enemigos = new ArrayList<>();
        enemigos.add(mock(NaveEnemiga.class));
        RondaConstruida estado = crear(enemigos, 1);
        enemigos.clear();
        assertEquals(1, estado.enemigos().size());
        estado.enemigos().clear();
        assertEquals(1, estado.enemigos().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rechazaCantidadDistintaDeLosEnemigosCreados() {
        crear(new ArrayList<>(), 1);
    }

    private RondaConstruida crear(List<NaveEnemiga> enemigos, int cantidad) {
        return new RondaConstruida(mock(SpaceNavigation.class), mock(GestorRecursos.class),
            mock(com.badlogic.gdx.graphics.OrthographicCamera.class),
            mock(com.badlogic.gdx.utils.viewport.Viewport.class), mock(Nave4.class),
            enemigos, 1, 0, 1, 1, cantidad);
    }
}
