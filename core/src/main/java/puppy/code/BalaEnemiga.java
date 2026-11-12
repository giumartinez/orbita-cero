package puppy.code;

import java.util.List;
import com.badlogic.gdx.graphics.Texture;

public class BalaEnemiga extends Bullet {
    public BalaEnemiga(float centroX, float centroY, float xSpeed, float ySpeed, Texture tx) {
        super(centroX, centroY, xSpeed, ySpeed, tx, false);
    }

    @Override
    public NaveEnemiga procesarColision(Nave4 nave, List<NaveEnemiga> enemigos) {
        nave.checkCollisionBalaEnemiga(this);
        return null;
    }
}
