package puppy.code;

public interface ConstructorPantallaJuego {
    ConstructorPantallaJuego ronda(int valor);
    ConstructorPantallaJuego vidas(int valor);
    ConstructorPantallaJuego score(int valor);
    ConstructorPantallaJuego velocidadEnemigos(int x, int y);
    ConstructorPantallaJuego cantidadEnemigos(int valor);
    PantallaJuego build();
}
