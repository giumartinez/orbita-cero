package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NaveEnemigaColisionableTest {
    private Texture textura() {
        Texture tx = mock(Texture.class);
        when(tx.getWidth()).thenReturn(64);
        when(tx.getHeight()).thenReturn(64);
        return tx;
    }

    @Test
    public void choqueIgnoraReferenciaNulaYEnemigoDestruido() {
        NaveCaza activa = new NaveCaza(10, 10, 3, 0, textura());
        NaveCaza destruida = new NaveCaza(10, 10, -2, 0, textura());
        destruida.recibirImpacto(1);

        activa.checkCollision(null);
        activa.checkCollision(destruida);

        assertEquals(3, activa.getXSpeed());
        assertEquals(-2, destruida.getXSpeed());
        assertTrue(activa.colisionaCon(activa));
        assertFalse(activa.colisionaCon(destruida));
    }

    @Test
    public void choqueEntreNavesActivasSuperpuestasLasSepara() {
        NaveCaza primera = new NaveCaza(10, 10, 3, 0, textura());
        NaveCaza segunda = new NaveCaza(30, 10, -2, 0, textura());

        assertTrue(primera.colisionaCon(segunda));
        primera.checkCollision(segunda);

        assertFalse(primera.colisionaCon(segunda));
    }
}
