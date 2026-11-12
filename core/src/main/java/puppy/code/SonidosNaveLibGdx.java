package puppy.code;

import java.util.Objects;
import com.badlogic.gdx.audio.Sound;

/** Adaptador de los eventos de la nave a los sonidos compartidos de LibGDX. */
final class SonidosNaveLibGdx implements SonidosNave {
    private final Sound disparo;
    private final Sound herida;

    SonidosNaveLibGdx(Sound disparo, Sound herida) {
        this.disparo = Objects.requireNonNull(disparo);
        this.herida = Objects.requireNonNull(herida);
    }

    @Override public void alDisparar() { disparo.play(); }
    @Override public void alRecibirDano() { herida.play(); }
}
