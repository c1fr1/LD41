package Game;

import static org.lwjgl.glfw.GLFW.*;

public class UserControls {
	public static int forward = GLFW_KEY_W;
	public static int backward = GLFW_KEY_S;
	public static int left = GLFW_KEY_A;
	public static int right = GLFW_KEY_D;
	public static int down = GLFW_KEY_LEFT_SHIFT;
	public static int up = GLFW_KEY_SPACE;
	public static int sdown = GLFW_KEY_DOWN;
	public static int sup = GLFW_KEY_UP;
	public static int sleft = GLFW_KEY_LEFT;
	public static int sright = GLFW_KEY_RIGHT;
	public static int restart = GLFW_KEY_R;
	public static int pause = GLFW_KEY_P;
	public static float sensitivity = 1f/500f;
	
/*
	if commonCamera is true, the camera rotation for most 3d games will be used, if it is false, this alternate method is used:

	every frame the new rotation matrix is set to the rotation matrix from relative mouse position changes (from the last frame),
	will be multiplied by the old rotation matrix.
	R(xnew-xold)*R(ynew-yold)*R(znew-zold)*oldRotationMatrix.
	
	oh btw
	this variable has been moved from this file, to the Camera file, so you can have different cameras with different types of cameras
																																		 */
}
