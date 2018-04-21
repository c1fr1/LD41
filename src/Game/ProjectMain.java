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
	
	public void setup() {
		//set variables here
	}
	
	public void gameLoop() {
		//game here
		
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
		EnigWindow.mainWindow.resetOffsets();
		glfwSwapBuffers(EnigWindow.mainWindow.window);
		glfwPollEvents();
		EnigWindow.checkGLError();
	}
}