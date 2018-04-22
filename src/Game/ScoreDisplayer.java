package Game;

import engine.EnigWindow;
import engine.Program;
import engine.Texture;

public class ScoreDisplayer {
	public static Texture[] numberTextures = new Texture[] {
			new Texture("res/01.png"),
			new Texture("res/23.png"),
			new Texture("res/45.png"),
			new Texture("res/67.png"),
			new Texture("res/89.png"),
	};
	public static Program numShader = new Program("res/shaders/numShaders/vert.gls", "res/shaders/numShaders/frag.gls");
	public static void render(int score) {
		numShader.enable();
		Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		Program.currentShaderProgram.shaders[0].uniforms[3].set(new float[] {0});
		int dig = 0;
		for (char c:("" + score).toCharArray()) {
			int num = Integer.parseInt(Character.toString(c));
			numberTextures[num/2].bind();
			if (num % 2 == 0) {
				Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {1f});
			}else {
				Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {0f});
			}
			Program.currentShaderProgram.shaders[0].uniforms[2].set(new float[] {(float) dig});
			TileCol.tileVAO.render();
			++dig;
		}
	}
	public static void render(int score, float offset) {
		numShader.enable();
		Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		
		Program.currentShaderProgram.shaders[0].uniforms[3].set(new float[] {offset});
		int dig = 0;
		for (char c:("" + score).toCharArray()) {
			int num = Integer.parseInt(Character.toString(c));
			numberTextures[num/2].bind();
			if (num % 2 == 0) {
				Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {1f});
			}else {
				Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {0f});
			}
			Program.currentShaderProgram.shaders[0].uniforms[2].set(new float[] {(float) dig});
			TileCol.tileVAO.render();
			++dig;
		}
	}
}
