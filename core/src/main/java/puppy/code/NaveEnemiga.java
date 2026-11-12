package puppy.code;

import java.util.List;
import java.util.Collections;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class NaveEnemiga implements Colisionable {
    private float x;
    private float y;
    private int xSpeed;
    private int ySpeed;
    private int puntosVida;
    private float intervaloDisparo;
    private float tiempoDisparo;
    private float rapidezProyectil;
    private Sprite spr;
    private EstrategiaDisparo estrategiaDisparo;

    protected NaveEnemiga(int x, int y, int ancho, int alto, int xSpeed, int ySpeed, int puntosVida, float intervaloDisparo, float rapidezProyectil, Texture tx, EstrategiaDisparo estrategiaDisparo) {
        this.puntosVida = puntosVida;
        this.intervaloDisparo = intervaloDisparo;
        this.tiempoDisparo = intervaloDisparo * 0.6f;
        this.rapidezProyectil = rapidezProyectil;
        this.estrategiaDisparo = estrategiaDisparo;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.spr = new Sprite(tx);
        this.spr.setSize(ancho, alto);
        this.spr.setOriginCenter();
        this.x = MathUtils.clamp(x, 0, AreaJuego.ANCHO - spr.getWidth());
        this.y = MathUtils.clamp(y, 0, AreaJuego.ALTO - spr.getHeight());
        this.spr.setPosition(this.x, this.y);
    }

    public List<Bullet> update(float delta, float objetivoX, float objetivoY, Texture txBala) {
        if (estaDestruido()) {
            return Collections.emptyList();
        }
        float paso = Math.min(delta, 0.05f) * 60f;
        x += xSpeed * paso;
        y += ySpeed * paso;
        if (x < 0 || x + spr.getWidth() > AreaJuego.ANCHO) {
            x = MathUtils.clamp(x, 0, AreaJuego.ANCHO - spr.getWidth());
            xSpeed = -xSpeed;
        }
        if (y < 0 || y + spr.getHeight() > AreaJuego.ALTO) {
            y = MathUtils.clamp(y, 0, AreaJuego.ALTO - spr.getHeight());
            ySpeed = -ySpeed;
        }
        spr.setPosition(x, y);
        spr.setRotation(DireccionDisparo.anguloNave(getCentroX(), getCentroY(), objetivoX, objetivoY));
        tiempoDisparo = Math.max(0f, tiempoDisparo - delta);
        if (tiempoDisparo == 0f) {
            List<Bullet> disparos = dispararHacia(objetivoX, objetivoY, txBala);
            tiempoDisparo = intervaloDisparo;
            return disparos;
        }
        return Collections.emptyList();
    }

    public List<Bullet> dispararHacia(float objetivoX, float objetivoY, Texture txBala) {
        return estrategiaDisparo.crearDisparos(getCentroX(), getCentroY(), objetivoX, objetivoY, rapidezProyectil, txBala, false);
    }

    public final boolean recibirImpacto(int dano) {
        if (!puedeRecibirImpacto(dano)) {
            return false;
        }
        aplicarDano(dano);
        alRecibirImpacto();
        return estaDestruido();
    }

    private boolean puedeRecibirImpacto(int dano) {
        return !estaDestruido() && dano > 0;
    }

    private void aplicarDano(int dano) {
        puntosVida = Math.max(0, puntosVida - dano);
    }

    public void checkCollision(NaveEnemiga otra) {
        Rectangle a = this.getArea();
        Rectangle b = otra.getArea();
        Vector2 desplazamiento = ColisionAsteroides.desplazamiento(a, b);
        if (desplazamiento.isZero()) {
            return;
        }
        Vector2[] separaciones = ColisionAsteroides.separaciones(a, b, AreaJuego.ANCHO, AreaJuego.ALTO);
        if (desplazamiento.x != 0) {
            float direccion = Math.signum(desplazamiento.x);
            int velocidadPropia = xSpeed;
            int velocidadOtra = otra.getXSpeed();
            aplicarChoque(separaciones[0], true, velocidadOtra, direccion);
            otra.aplicarChoque(separaciones[1], true, velocidadPropia, -direccion);
        } else {
            float direccion = Math.signum(desplazamiento.y);
            int velocidadPropia = ySpeed;
            int velocidadOtra = otra.getySpeed();
            aplicarChoque(separaciones[0], false, velocidadOtra, direccion);
            otra.aplicarChoque(separaciones[1], false, velocidadPropia, -direccion);
        }
    }

    private void aplicarChoque(Vector2 separacion, boolean horizontal, int velocidadOtra, float direccion) {
        x += separacion.x;
        y += separacion.y;
        if (horizontal) {
            xSpeed = ColisionAsteroides.velocidadesTrasChoque(xSpeed, velocidadOtra, direccion)[0];
        } else {
            ySpeed = ColisionAsteroides.velocidadesTrasChoque(ySpeed, velocidadOtra, direccion)[0];
        }
        x = MathUtils.clamp(x, 0, AreaJuego.ANCHO - spr.getWidth());
        y = MathUtils.clamp(y, 0, AreaJuego.ALTO - spr.getHeight());
        spr.setPosition(x, y);
    }

    public void rebotarContraJugador() {
        xSpeed = -xSpeed;
        ySpeed = -ySpeed;
    }

    public void draw(SpriteBatch batch) {
        if (!estaDestruido()) {
            spr.draw(batch);
        }
    }

    public void cambiarColor(float r, float g, float b) {
        spr.setColor(r, g, b, 1.0f);
    }

    public void setColor(float r, float g, float b) {
        spr.setColor(r, g, b, 1.0f);
    }

    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    @Override
    public boolean estaDestruido() {
        return puntosVida <= 0;
    }

    public float getCentroX() {
        return x + spr.getWidth() / 2f;
    }

    public float getCentroY() {
        return y + spr.getHeight() / 2f;
    }

    public abstract int getPuntajeRecompensa();
    public abstract String getNombreTipo();
    protected void alRecibirImpacto() {
        // Hook opcional para reacciones propias de cada tipo de nave.
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public float getIntervaloDisparo() {
        return intervaloDisparo;
    }

    public void setIntervaloDisparo(float intervaloDisparo) {
        this.intervaloDisparo = Math.max(0.2f, intervaloDisparo);
    }

    public EstrategiaDisparo getEstrategiaDisparo() {
        return estrategiaDisparo;
    }

    public void setEstrategiaDisparo(EstrategiaDisparo estrategiaDisparo) {
        this.estrategiaDisparo = estrategiaDisparo;
    }
}
