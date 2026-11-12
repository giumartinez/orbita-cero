package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NaveEnemigaReboteTest {
    @Test
    public void reboteContraJugadorInvierteAmbasVelocidades() {
        Texture textura = mock(Texture.class);
        when(textura.getWidth()).thenReturn(64);
        when(textura.getHeight()).thenReturn(64);
        NaveEnemiga enemigo = new NaveCaza(40, 40, 3, -2, textura);

        enemigo.rebotarContraJugador();

        assertEquals(-3, enemigo.getXSpeed());
        assertEquals(2, enemigo.getySpeed());
    }
}
