package puppy.code;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import puppy.code.input.ControlesTecladoNave;

public final class PantallaJuegoBuilder implements ConstructorPantallaJuego {
    private final SpaceNavigation game;
    private final FabricaEnemigos fabricaEnemigos;
    private final Random random;
    private int ronda = 1, vidas = 3, score, velXEnemigos = 1, velYEnemigos = 1, cantidadEnemigos = 10;

    public PantallaJuegoBuilder(SpaceNavigation game) {
        this(game, new FabricaEnemigosRonda(GestorRecursos.getInstancia()), new Random());
    }

    PantallaJuegoBuilder(SpaceNavigation game, FabricaEnemigos fabricaEnemigos, Random random) {
        this.game = Objects.requireNonNull(game);
        this.fabricaEnemigos = Objects.requireNonNull(fabricaEnemigos);
        this.random = Objects.requireNonNull(random);
    }

    @Override public ConstructorPantallaJuego ronda(int valor) { ronda = positivo(valor); return this; }
    @Override public ConstructorPantallaJuego vidas(int valor) { vidas = positivo(valor); return this; }
    @Override public ConstructorPantallaJuego score(int valor) { score = noNegativo(valor); return this; }
    @Override public ConstructorPantallaJuego velocidadEnemigos(int x, int y) {
        velXEnemigos = positivo(x); velYEnemigos = positivo(y); return this;
    }
    @Override public ConstructorPantallaJuego cantidadEnemigos(int valor) {
        cantidadEnemigos = positivo(valor); return this;
    }

    @Override public PantallaJuego build() {
        GestorRecursos recursos = GestorRecursos.getInstancia();
        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(AreaJuego.ANCHO, AreaJuego.ALTO, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Nave4 nave = new Nave4(400 - 22, 30, recursos.getTexturaNave(),
            recursos.getTexturaBala(), new SonidosNaveLibGdx(recursos.getSonidoDisparo(), recursos.getSonidoHerido()),
            vidas, new ControlesTecladoNave(viewport), ronda >= 3 ? new DisparoTriple() : new DisparoSimple());
        ArrayList<NaveEnemiga> enemigos = new ArrayList<>();
        for (int i = 0; i < cantidadEnemigos; i++) {
            int x = random.nextInt(AreaJuego.ANCHO - 60);
            int y = 160 + random.nextInt(AreaJuego.ALTO - 220);
            int vx = velXEnemigos + random.nextInt(3);
            int vy = velYEnemigos + random.nextInt(3);
            enemigos.add(fabricaEnemigos.crear(i, x, y, vx, vy));
        }
        return new PantallaJuego(new RondaConstruida(game, recursos, camera, viewport, nave,
            enemigos, ronda, score, velXEnemigos, velYEnemigos, cantidadEnemigos));
    }

    private static int positivo(int valor) {
        if (valor <= 0) throw new IllegalArgumentException("Se requiere un valor positivo");
        return valor;
    }
    private static int noNegativo(int valor) {
        if (valor < 0) throw new IllegalArgumentException("Se requiere un valor no negativo");
        return valor;
    }
}
