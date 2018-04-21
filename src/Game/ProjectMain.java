package Game;

import engine.*;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class ProjectMain {

	private int frame = 0;
	
	public static ProjectMain main;
	
	//project variables
	Texture boxTexture;
	Texture playerTexture;
	Program blockProgram;
	Program laserProgram;
	TileCol[] columns;
	ArrayList<Laser> lasers = new ArrayList<Laser>();
	public Player mainPlayer;
	public static int score = 0;
	public static int mult = 1;
	
	public void setup() {
		//set variables here
		boxTexture = new Texture("res/block.png");
		playerTexture = new Texture("res/char.png");
		blockProgram = new Program("res/shaders/blockShaders/vert.gls", "res/shaders/blockShaders/frag.gls");
		laserProgram = new Program("res/shaders/laserShaders/vert.gls", "res/shaders/laserShaders/frag.gls");
		columns = new TileCol[999];
		for (int i = 0; i < columns.length;++i) {
			columns[i] = new TileCol((float) i * 800);
		}
		mainPlayer = new Player();
		
	}
	
	public void gameLoop() {
		//game here
		int idx = (int) (mainPlayer.xpos/800);
		if (idx > mult) {
			mult = idx;
		}
		TileCol mainTileCol = columns[idx];
		blockProgram.enable();
		boxTexture.bind();
		Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		for (int i = -5; i < 5; ++i) {
			if (i + idx >= 0) {
				TileCol col = columns[i + idx];
				col.render(mainPlayer.xpos, mainPlayer.ypos);
			}
		}
		mainPlayer.updateMovement(mainTileCol);
		playerTexture.bind();
		mainPlayer.selection.bindColor();
		Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {-TileCol.BOX_SIZE, -TileCol.BOX_SIZE});
		TileCol.tileVAO.render();
		//System.out.println(score);
		laserProgram.enable();
		Laser.laserTexture.bind();
		Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		for (Laser englishPaper:lasers) {
			englishPaper.updatePos(mainTileCol, mainPlayer.xpos, mainPlayer.ypos);
			englishPaper.render(mainPlayer.xpos, mainPlayer.ypos);
		}
		double rand = Math.random();
		double leg = Math.pow(1-1/Math.log(Math.E + score), 16);
		if (rand < leg) {
			lasers.add(new Laser(mainPlayer.xpos, mainPlayer.ypos));
			System.out.println(lasers.size());
		}
		//System.out.println(lasers.size());
		for (int i = lasers.size() - 1; i > 0;--i) {
			if (lasers.get(i).endThis) {
				lasers.remove(i);
			}
		}
	}
	
	public static void main(String[] args) {
		main = new ProjectMain();
	}
/*															end															*/
	public ProjectMain() {
		new EnigWindow();
		frame = 0;
		setup();
		while ( !glfwWindowShouldClose(EnigWindow.mainWindow.window) ) {
			EnigWindow.mainWindow.update();
			++frame;
			gameLoop();
			cleanup();
		}
		EnigWindow.mainWindow.terminate();
	}
	
	public void cleanup() {
		Program.disable();
		Texture.unbind();
		EnigWindow.mainWindow.resetOffsets();
		glfwSwapBuffers(EnigWindow.mainWindow.window);
		glfwPollEvents();
		EnigWindow.checkGLError();
	}
}