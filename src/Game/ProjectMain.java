package Game;

import engine.*;
import engine.Platform.*;
import engine.Functions.*;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class ProjectMain {

	private int frame = 0;
	
	public static ProjectMain Main;
	
	//project variables
	VAO test;
	Texture boxTexture;
	Program blockProgram;
	
	public void setup() {
		//set variables here
		test = new VAO(-250f, -250f, 500f, 500f);
		boxTexture = new Texture("res/block.png");
		blockProgram = new Program("res/shaders/blockShaders/vert.gls", "res/shaders/blockShaders/frag.gls");
	}
	
	public void gameLoop() {
		//game here
		blockProgram.enable();
		boxTexture.bind();
		blockProgram.shaders[2].uniforms[0].set(new float[] {0f, 1f, 0f});
		blockProgram.shaders[0].uniforms[0].set(new float[] {EnigWindow.width, EnigWindow.height});
		test.render();
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