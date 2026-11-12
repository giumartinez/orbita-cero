package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GestorRecursosTest {
    @Test public void liberaRecursosPropiosUnaVezYPermiteReiniciarInstancia() throws Exception {
        GestorRecursos gestor = mock(GestorRecursos.class);
        Texture principal = mock(Texture.class);
        Texture bala = mock(Texture.class);
        Texture artillera = mock(Texture.class);
        Sound explosion = mock(Sound.class);
        Sound herido = mock(Sound.class);
        Sound disparo = mock(Sound.class);
        Music musica = mock(Music.class);

        asignar(gestor, "texturaNave", principal);
        asignar(gestor, "texturaBala", bala);
        asignar(gestor, "texturaNaveCaza", principal); // Respaldo compartido.
        asignar(gestor, "texturaNaveArtillera", artillera);
        asignar(gestor, "sonidoExplosion", explosion);
        asignar(gestor, "sonidoHerido", herido);
        asignar(gestor, "sonidoDisparo", disparo);
        asignar(gestor, "musicaJuego", musica);
        asignar(null, "instancia", gestor);

        try {
            assertSame(gestor, GestorRecursos.getInstancia());
            assertSame(gestor, GestorRecursos.getInstancia());
            GestorRecursos.liberar();
            GestorRecursos.liberar();
            verify(principal, times(1)).dispose();
            verify(bala, times(1)).dispose();
            verify(artillera, times(1)).dispose();
            verify(explosion, times(1)).dispose();
            verify(herido, times(1)).dispose();
            verify(disparo, times(1)).dispose();
            verify(musica, times(1)).dispose();
            assertNull(campo("instancia").get(null));
        } finally {
            campo("instancia").set(null, null);
        }
    }

    private static Field campo(String nombre) throws Exception {
        Field campo = GestorRecursos.class.getDeclaredField(nombre);
        campo.setAccessible(true);
        return campo;
    }

    private static void asignar(GestorRecursos gestor, String nombre, Object valor) throws Exception {
        campo(nombre).set(gestor, valor);
    }
}
