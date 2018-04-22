package Game;

import engine.Program;
import engine.Texture;
import org.joml.Vector3f;

public class Laser {
	private float xpos, ypos;
	private float xVel;
	public static Texture laserTexture = new Texture("res/laser.png");
	public TileCol.TileColor color;
	public boolean endThis = false;
	public Laser(float px, float py) {
		if (Math.random() > 0.5) {
			xpos = px - 5000;
			xVel = 25;
		}else {
			xpos = px + 5000;
			xVel = -25;
		}
		float targetY = Math.round(py/TileCol.BOX_SIZE) * TileCol.BOX_SIZE;
		float row = Math.round(Math.random() * 6) - 3;
		ypos = targetY + row * TileCol.BOX_SIZE;
		color = TileCol.TileColor.getRandom();
	}
	public void render(float xPos, float yPos) {
		Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {xpos - xPos, ypos - yPos});
		color.bindColor();
		TileCol.tileVAO.render();
	}
	public void updatePos(TileCol col, float px, float py) {
		xpos += xVel;
		if ((int) (xpos/800) == (int) (col.posx/800)) {
			col.setPoint(xpos % 800, ypos, color);
		}
		if ((xpos - px)*(xpos - px) + (ypos - py + 100)*(ypos - py + 100) < 100) {
			for (float i = -1; i < 1.5f;++i) {
				for (float j = -2; j < 0.5f;++j) {
					col.setPoint((px + i * TileCol.BOX_SIZE) % 800, py + j * TileCol.BOX_SIZE, color);
				}
			}
			ProjectMain.main.mainPlayer.selection = color;
			endThis = true;
		}
		if ((xpos - px)/xVel > 200) {
			endThis = true;
		}
	}
}
