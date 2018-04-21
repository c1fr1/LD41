package Game;

import engine.*;

import static org.lwjgl.glfw.GLFW.*;

public class ProjectMain {

	private int frame = 0;
	
	public static ProjectMain Main;
	
	//project variables
	Texture boxTexture;
	Texture playerTexture;
	Program blockProgram;
	TileCol mainTileCol;
	Player mainPlayer;
	
	public void setup() {
		//set variables here
		boxTexture = new Texture("res/block.png");
		playerTexture = new Texture("res/char.png");
		blockProgram = new Program("res/shaders/blockShaders/vert.gls", "res/shaders/blockShaders/frag.gls");
		mainTileCol = new TileCol(5f);
		mainPlayer = new Player();
	}
	
	public void gameLoop() {
		//game here
		blockProgram.enable();
		boxTexture.bind();
		Program.currentShaderProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		mainTileCol.render(mainPlayer.xpos, mainPlayer.ypos);
		mainPlayer.updateMovement(mainTileCol);
		playerTexture.bind();
		mainPlayer.selection.bindColor();
		Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {-TileCol.BOX_SIZE, -TileCol.BOX_SIZE});
		TileCol.tileVAO.render();
		
	}
	
	public static void main(String[] args) {
		Main = new ProjectMain();
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