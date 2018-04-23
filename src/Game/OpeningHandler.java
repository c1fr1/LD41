package Game;

import engine.*;

import static org.lwjgl.glfw.GLFW.*;

public class OpeningHandler {
	Program tutProgram = new Program("res/shaders/textureShaders/vert.gls", "res/shaders/textureShaders/frag.gls");
	Program buttonProgram = new Program("res/shaders/buttonShaders/vert.gls", "res/shaders/buttonShaders/frag.gls");
	
	Texture title = new Texture("res/title.png");
	Texture tutorialButton = new Texture("res/tut.png");
	Texture startGameButton = new Texture("res/startGame.png");
	
	Texture tex1 = new Texture("res/tut1.png");
	Texture tex2 = new Texture("res/tut2.png");
	Texture tex3 = new Texture("res/tut3.png");
	Texture tex4 = new Texture("res/tut4.png");
	
	VAO back;
	VAO titleObj;
	VAO buttona;
	VAO buttonb;
	public OpeningHandler() {
		titleObj = new VAO(-0.5f, 0.5f, 1f, EnigWindow.width/(4f*EnigWindow.height));
		buttona = new VAO(-0.25f, -0.2f, 0.5f, EnigWindow.width/(8f*EnigWindow.height));
		buttonb = new VAO(-0.25f, -0.5f, 0.5f, EnigWindow.width/(8f*EnigWindow.height));
		back = new VAO(-1f, -1f, 2f, 2f);
		boolean tut = false;
		while (!glfwWindowShouldClose(EnigWindow.mainWindow.window)) {
			EnigWindow.mainWindow.update();
			System.out.println("dx: " + EnigWindow.mainWindow.cursorXFloat + " dy: " + EnigWindow.mainWindow.cursorYFloat);
			tutProgram.enable();
			title.bind();
			titleObj.render();
			buttonProgram.enable();
			buttonProgram.shaders[2].uniforms[0].set(new float[] {1f, 0f, 0f, 1f});
			float cx = EnigWindow.mainWindow.cursorXFloat;
			float cy = EnigWindow.mainWindow.cursorYFloat;
			if (cx > -0.25 && cx < 0.25 && cy > -0.2 && cy < -0.2 + EnigWindow.width/(8f*EnigWindow.height)) {
				buttonProgram.shaders[2].uniforms[0].set(new float[] {1f, 0f, 0f, 1f});
				if (EnigWindow.mainWindow.mouseButtons[GLFW_MOUSE_BUTTON_LEFT] == 1) {
					tut = true;
					break;
				}
			}else {
				buttonProgram.shaders[2].uniforms[0].set(new float[] {0f, 0f, 0f, 0f});
			}
			tutorialButton.bind();
			buttona.render();
			if (cx > -0.25 && cx < 0.25 && cy > -0.5 && cy < -0.5 + EnigWindow.width/(8f*EnigWindow.height)) {
				buttonProgram.shaders[2].uniforms[0].set(new float[] {1f, 0f, 0f, 1f});
				if (EnigWindow.mainWindow.mouseButtons[GLFW_MOUSE_BUTTON_LEFT] == 1) {
					tut = false;
					break;
				}
			}else {
				buttonProgram.shaders[2].uniforms[0].set(new float[]{0f, 0f, 0f, 0f});
			}
			startGameButton.bind();
			buttonb.render();
			cleanup();
		}
		cleanup();
		if (tut) {
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] == 0 && !glfwWindowShouldClose(EnigWindow.mainWindow.window)) {
				EnigWindow.mainWindow.update();
				tutProgram.enable();
				tex1.bind();
				back.render();
				cleanup();
			}
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] != 0) {
				glfwPollEvents();
			}
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] == 0 && !glfwWindowShouldClose(EnigWindow.mainWindow.window)) {
				EnigWindow.mainWindow.update();
				tutProgram.enable();
				tex2.bind();
				back.render();
				cleanup();
			}
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] != 0) {
				glfwPollEvents();
			}
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] == 0 && !glfwWindowShouldClose(EnigWindow.mainWindow.window)) {
				EnigWindow.mainWindow.update();
				tutProgram.enable();
				tex3.bind();
				back.render();
				cleanup();
			}
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] != 0) {
				glfwPollEvents();
			}
			while (EnigWindow.mainWindow.keys[GLFW_KEY_SPACE] == 0 && !glfwWindowShouldClose(EnigWindow.mainWindow.window)) {
				EnigWindow.mainWindow.update();
				tutProgram.enable();
				tex4.bind();
				back.render();
				cleanup();
			}
		}
		cleanup();
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
