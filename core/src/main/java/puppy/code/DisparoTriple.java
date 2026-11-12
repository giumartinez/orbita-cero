package puppy.code;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

//estrategia concreta, disparo triple (gm7)

public class DisparoTriple implements EstrategiaDisparo {
    private float anguloDispersion;

    public DisparoTriple(){
        this(16.0f);
    }

    public DisparoTriple(float anguloDispersion) {
        setAnguloDispersion(anguloDispersion);
    }

    @Override
    public List<Bullet> crearDisparos(float origenX, float origenY, float objetivoX, float objetivoY, float rapidezBala, Texture txBala, boolean origenJugador) {
        
        List<Bullet> disparos = new ArrayList<>();
        
        Vector2 velocidadCentral = DireccionDisparo.velocidad(origenX, origenY, objetivoX, objetivoY, rapidezBala);
        
        if(velocidadCentral.isZero()){
            return disparos;
        }
        
        //disparo central
        disparos.add(crearBala(origenX, origenY, velocidadCentral.x, velocidadCentral.y, txBala, origenJugador));
        
        //disparo izquierdo
        Vector2 velocidadIzquierda = velocidadCentral.cpy().rotateDeg(-anguloDispersion);
        disparos.add(crearBala(origenX, origenY, velocidadIzquierda.x, velocidadIzquierda.y, txBala, origenJugador));
        
        //disparo derecho
        Vector2 velocidadDerecha = velocidadCentral.cpy().rotateDeg(anguloDispersion);
        disparos.add(crearBala(origenX, origenY, velocidadDerecha.x, velocidadDerecha.y, txBala, origenJugador));

        return disparos;
    }

    private Bullet crearBala(float x, float y, float vx, float vy, Texture textura, boolean origenJugador) {
        return origenJugador ? new Bullet(x, y, vx, vy, textura) : new BalaEnemiga(x, y, vx, vy, textura);
    }

    public float getAnguloDispersion() {
        return anguloDispersion;
    }

    public void setAnguloDispersion(float anguloDispersion) {
        if(anguloDispersion <= 0 || anguloDispersion > 90){
            throw new IllegalArgumentException("El ángulo de dispersión debe estar entre 0 y 90 grados.");
        }
        this.anguloDispersion = anguloDispersion;
    }
}
