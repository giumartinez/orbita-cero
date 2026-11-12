package puppy.code;

import java.util.Objects;

public final class DirectorRondas {
    private DirectorRondas() { }

    public static PantallaJuego iniciar(ConstructorPantallaJuego builder) {
        return Objects.requireNonNull(builder).ronda(1).vidas(3).score(0)
            .velocidadEnemigos(1, 1).cantidadEnemigos(10).build();
    }

    public static PantallaJuego avanzar(ConstructorPantallaJuego builder, int ronda,
            int vidas, int score, int velocidadX, int velocidadY, int cantidad) {
        return Objects.requireNonNull(builder).ronda(Math.addExact(ronda, 1)).vidas(vidas)
            .score(score).velocidadEnemigos(Math.addExact(velocidadX, 1), Math.addExact(velocidadY, 1))
            .cantidadEnemigos(Math.addExact(cantidad, 2)).build();
    }
}
