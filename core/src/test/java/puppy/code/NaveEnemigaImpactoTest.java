package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NaveEnemigaImpactoTest {
    private Texture textura() {
        Texture tx = mock(Texture.class);
        when(tx.getWidth()).thenReturn(64);
        when(tx.getHeight()).thenReturn(64);
        return tx;
    }

    @Test
    public void cazaIgnoraDanoInvalidoYNoRecibeMasTrasDestruccion() {
        NaveCaza caza = new NaveCaza(10, 10, 0, 0, textura());
        assertFalse(caza.recibirImpacto(0));
        assertFalse(caza.recibirImpacto(-1));
        assertEquals(1, caza.getPuntosVida());
        assertTrue(caza.recibirImpacto(1));
        assertEquals(0, caza.getPuntosVida());
        assertFalse(caza.recibirImpacto(1));
    }

    @Test
    public void artilleraReaccionaUnaVezAlQuedarConUnaVida() {
        NaveArtillera artillera = new NaveArtillera(10, 10, 0, 0, textura());
        float intervaloInicial = artillera.getIntervaloDisparo();
        assertFalse(artillera.recibirImpacto(1));
        assertEquals(1, artillera.getPuntosVida());
        assertEquals(intervaloInicial * 0.5f, artillera.getIntervaloDisparo(), 0.0001f);
        assertTrue(artillera.recibirImpacto(1));
        assertEquals(0, artillera.getPuntosVida());
        assertEquals(intervaloInicial * 0.5f, artillera.getIntervaloDisparo(), 0.0001f);
        assertFalse(artillera.recibirImpacto(1));
    }
}
