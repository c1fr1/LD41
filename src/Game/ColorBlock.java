package Game;

import engine.Program;

import java.awt.*;

public class ColorBlock {
	public float[] changedColor = new float[] {0f, 0f, 0f};
	public TileColor clr;
	public ColorBlock() {
		clr = TileColor.getRandom();
	}
	enum TileColor {
		red, green, blue, yellow;
		public static float[] redColor = new float[] {1f, 0f, 0f};
		public static float[] greenColor = new float[] {0f, 1f, 0f};
		public static float[] blueColor = new float[] {0f, 0f, 1f};
		public static float[] yellowColor = new float[] {1f, 1f, 0f};
		float[] realColor;
		public static TileColor getRandom() {
			TileColor ret = red;
			double rand = Math.random();
			if (rand < 0.25) {
				red.realColor =  redColor;
			}else if (rand < 0.5) {
				ret = green;
				ret.realColor = greenColor;
			}else if (rand < 0.75) {
				ret = blue;
				ret.realColor = blueColor;
			}else {
				ret = yellow;
				ret.realColor = yellowColor;
			}
			return ret;
		}
		public void bindColor() {
			Program.currentShaderProgram.shaders[2].uniforms[0].set(realColor);
		}
	}
	public void bindColor() {
		Program.currentShaderProgram.shaders[2].uniforms[0].set(clr.realColor);
		Program.currentShaderProgram.shaders[2].uniforms[1].set(changedColor);
		changedColor[0] *= 0.95;
		changedColor[1] *= 0.95;
		changedColor[2] *= 0.95;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((ColorBlock) obj).clr.equals(clr);
	}
}
