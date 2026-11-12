package puppy.code;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DirectorRondasTest {
    @Test public void inicioConfiguraLaPrimeraRonda() {
        ConstructorPantallaJuego builder = mock(ConstructorPantallaJuego.class, RETURNS_SELF);
        PantallaJuego producto = mock(PantallaJuego.class);
        when(builder.build()).thenReturn(producto);
        assertSame(producto, DirectorRondas.iniciar(builder));
        verify(builder).ronda(1);
        verify(builder).vidas(3);
        verify(builder).score(0);
        verify(builder).velocidadEnemigos(1, 1);
        verify(builder).cantidadEnemigos(10);
        verify(builder).build();
    }

    @Test public void avanceConservaProgresoYAumentaDificultad() {
        ConstructorPantallaJuego builder = mock(ConstructorPantallaJuego.class, RETURNS_SELF);
        PantallaJuego producto = mock(PantallaJuego.class);
        when(builder.build()).thenReturn(producto);
        assertSame(producto, DirectorRondas.avanzar(builder, 2, 2, 150, 3, 4, 12));
        verify(builder).ronda(3);
        verify(builder).vidas(2);
        verify(builder).score(150);
        verify(builder).velocidadEnemigos(4, 5);
        verify(builder).cantidadEnemigos(14);
        verify(builder).build();
    }
}
