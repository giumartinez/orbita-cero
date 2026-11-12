package puppy.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

final class RondaConstruida {
    private final SpaceNavigation game;
    private final GestorRecursos recursos;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Nave4 nave;
    private final ArrayList<NaveEnemiga> enemigos;
    private final int ronda, score, velXEnemigos, velYEnemigos, cantidadEnemigos;

    RondaConstruida(SpaceNavigation game, GestorRecursos recursos, OrthographicCamera camera,
            Viewport viewport, Nave4 nave, List<NaveEnemiga> enemigos, int ronda, int score,
            int velXEnemigos, int velYEnemigos, int cantidadEnemigos) {
        this.game = Objects.requireNonNull(game);
        this.recursos = Objects.requireNonNull(recursos);
        this.camera = Objects.requireNonNull(camera);
        this.viewport = Objects.requireNonNull(viewport);
        this.nave = Objects.requireNonNull(nave);
        this.enemigos = new ArrayList<>(Objects.requireNonNull(enemigos));
        if (ronda <= 0 || score < 0 || velXEnemigos <= 0 || velYEnemigos <= 0
                || cantidadEnemigos <= 0 || enemigos.size() != cantidadEnemigos) {
            throw new IllegalArgumentException("Configuración de ronda inválida");
        }
        this.ronda = ronda;
        this.score = score;
        this.velXEnemigos = velXEnemigos;
        this.velYEnemigos = velYEnemigos;
        this.cantidadEnemigos = cantidadEnemigos;
    }
    SpaceNavigation game() { return game; }
    GestorRecursos recursos() { return recursos; }
    OrthographicCamera camera() { return camera; }
    Viewport viewport() { return viewport; }
    Nave4 nave() { return nave; }
    ArrayList<NaveEnemiga> enemigos() { return new ArrayList<>(enemigos); }
    int ronda() { return ronda; }
    int score() { return score; }
    int velXEnemigos() { return velXEnemigos; }
    int velYEnemigos() { return velYEnemigos; }
    int cantidadEnemigos() { return cantidadEnemigos; }
}
