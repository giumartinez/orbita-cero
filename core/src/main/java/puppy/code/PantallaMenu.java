package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
		game.getFont().draw(game.getBatch(), "Orbita Cero: ultima guardia del corredor", 100, 470);
		game.getFont().draw(game.getBatch(), "La ruta de evacuacion sigue abierta mientras resistas.", 100, 410);
		game.getFont().draw(game.getBatch(), "Despeja cada oleada enemiga para mantenerla a salvo.", 100, 370);
		game.getFont().draw(game.getBatch(), "WASD: mover   Mouse: apuntar   Clic izquierdo: disparar", 100, 330);
		game.getFont().draw(game.getBatch(), "Haz clic o presiona cualquier tecla para comenzar ...", 100, 270);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = DirectorRondas.iniciar(new PantallaJuegoBuilder(game));
			ss.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			game.setScreen(ss);
			dispose();
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
   
}
