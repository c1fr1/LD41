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
	Texture overTexture;
	Program blockProgram;
	Program laserProgram;
	Program backgroundProgram;
	Program endProgram;
	Program wallProgram;
	TileCol[] columns;
	ArrayList<Laser> lasers = new ArrayList<Laser>();
	VAO backgroundObejct;
	VAO endObject;
	float[] backgroundColor;
	public Player mainPlayer;
	public static int score = 0;
	public static int mult = 1;
	public static int nextGoal = 2;
	public static float wallpos = 2300;
	public static float camX = 250;
	public static float camY = 2200;
	private int resFrames = 10;
	
	public void setup() {
		//set variables here
		boxTexture = new Texture("res/block.png");
		playerTexture = new Texture("res/char.png");
		overTexture = new Texture("res/gameOver.png");
		blockProgram = new Program("res/shaders/blockShaders/vert.gls", "res/shaders/blockShaders/frag.gls");
		laserProgram = new Program("res/shaders/laserShaders/vert.gls", "res/shaders/laserShaders/frag.gls");
		backgroundProgram = new Program("res/shaders/backgroundShaders/vert.gls", "res/shaders/backgroundShaders/frag.gls");
		endProgram = new Program("res/shaders/endShaders/vert.gls", "res/shaders/endShaders/frag.gls");
		wallProgram = new Program("res/shaders/wallShaders/vert.gls", "res/shaders/wallShaders/frag.gls");
		backgroundObejct = new VAO(-1f, -1f, 2f, 2f);
		endObject = new VAO(-500, -500, 1000, 1000);
		backgroundColor = new float[] {0.3f, 0f, 0f};
		columns = new TileCol[999];
		for (int i = 0; i < columns.length;++i) {
			columns[i] = new TileCol((float) i * 800);
		}
		mainPlayer = new Player();
		
	}
	
	public void gameLoop() {
		//game here
		//render backgournd
		for (int i = 0; i < backgroundColor.length;++i) {
			backgroundColor[i] *= 0.99;
			backgroundColor[i] += mainPlayer.selection.realColor[i] * 0.005f;
		}
		backgroundProgram.enable();
		if (mainPlayer.ypos < -50) {
			backgroundColor[0] += 0.2f;
			backgroundColor[1] += 0.2f;
			backgroundColor[2] += 0.2f;
		}
		camX += (mainPlayer.xpos - camX) * 0.1;
		camY += (mainPlayer.ypos - camY) * 0.1;
		Program.currentShaderProgram.shaders[2].uniforms[0].set(backgroundColor);
		Program.currentShaderProgram.shaders[2].uniforms[1].set(new float[] {EnigWindow.width, EnigWindow.height});
		backgroundObejct.render();
		
		//render columns
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
				col.render(camX, camY);
			}
		}
		//update and render player
		mainPlayer.updateMovement(mainTileCol);
		if (mainPlayer.ypos > -50) {
			playerTexture.bind();
			mainPlayer.selection.bindColor();
			Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {mainPlayer.xpos - camX-TileCol.BOX_SIZE, mainPlayer.ypos - camY-TileCol.BOX_SIZE});
			TileCol.tileVAO.render();
		}else {
			endProgram.enable();
			overTexture.bind();
			Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
			endObject.render();
		}
		//lasers
		laserProgram.enable();
		Laser.laserTexture.bind();
		Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		for (Laser englishPaper:lasers) {
			englishPaper.updatePos(mainTileCol, mainPlayer.xpos, mainPlayer.ypos);
			englishPaper.render(camX, camY);
		}
		double rand = Math.random();
		double leg = Math.pow(1-1/Math.log(Math.E + score), 32);
		if (rand < leg) {
			lasers.add(new Laser(mainPlayer.xpos, mainPlayer.ypos));
		}
		//System.out.println(lasers.size());
		for (int i = lasers.size() - 1; i > 0;--i) {
			if (lasers.get(i).endThis) {
				lasers.remove(i);
			}
		}
		//show score
		
		if (mainPlayer.ypos > -50) {
			wallpos += 0.1 * (700 + 800*nextGoal - wallpos);
			wallProgram.enable();
			Program.currentShaderProgram.shaders[2].uniforms[0].set(new float[]{EnigWindow.width, EnigWindow.height});
			Program.currentShaderProgram.shaders[2].uniforms[1].set(new float[]{wallpos - camX + EnigWindow.width / 2});//2250
			backgroundObejct.render();
		}
		
		ScoreDisplayer.render(score);
		
		
		if (mainPlayer.ypos > -50) {
			while (score > nextGoal * (nextGoal + 1) * 5) {
				++nextGoal;
			}
			ScoreDisplayer.render(nextGoal * (nextGoal + 1) * 5, wallpos - camX + EnigWindow.width / 2);
		}
		
		if (EnigWindow.mainWindow.keys[UserControls.restart] > 0) {
			resFrames = 60;
		}
		if (resFrames > 0) {
			camX = 250;
			camY = 2200;
			for (int i = 0; i < backgroundColor.length;++i) {
				backgroundColor[i] *= 0.97;
				backgroundColor[i] += mainPlayer.selection.realColor[i] * 0.015f;
			}
			score = 0;
			mult = 1;
			nextGoal = 2;
			columns = new TileCol[999];
			for (int i = 0; i < columns.length;++i) {
				columns[i] = new TileCol((float) i * 800);
			}
			mainPlayer = new Player();
			--resFrames;
			lasers = new ArrayList<>();
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
		main = this;
		new OpeningHandler();
		while (!glfwWindowShouldClose(EnigWindow.mainWindow.window) ) {
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