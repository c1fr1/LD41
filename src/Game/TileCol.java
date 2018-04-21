package Game;

import engine.EnigUtils;
import engine.EnigWindow;
import engine.Program;
import engine.VAO;

public class TileCol {
	public TileColor[][] colors;//4 wide, 20 high?
	public float posx;
	public static float BOX_SIZE = 100f;
	public static VAO tileVAO = new VAO(0f, 0f, BOX_SIZE, BOX_SIZE);
	public TileCol(float xOffset) {
		colors = new TileColor[4][20];
		for (int set = 0;set<colors.length;++set) {
			for (int tile = 0;tile < colors[set].length;++tile) {
				colors[set][tile] = TileColor.getRandom();
			}
		}
	}
	public void render(float px, float py) {
		float xo = posx - px;
		for (int x = 0; x < colors.length;++x) {
			for (int y = 0; y < colors[x].length;++y) {
				//System.out.println("xpos: " + xpos + " ypos: " + ypos);
				colors[x][y].bindColor();
				Program.currentShaderProgram.shaders[0].uniforms[1].set(new float[] {(float) x * BOX_SIZE + xo, (float) y*BOX_SIZE - py});
				tileVAO.render();
			}
		}
	}
	public TileColor checkPoint(float x, float y) {
		int xind = -1 + (int) (x/BOX_SIZE);
		if (xind < 0) {
			return null;
		}
		if (xind >= colors.length) {
			return null;
		}
		int yind = (int) (y/BOX_SIZE);
		if (yind < 0) {
			return null;
		}
		if (yind >= colors[0].length) {
			return null;
		}
		return colors[xind][yind];
	}
	public int countNeighbors(int x, int y) {
		TileColor thisColor = colors[x][y];
		int[] prevx = new int[] {x};
		int[] prevy = new int[] {y};
		int ret = 0;
		if (x >= 1) {
			if (colors[x - 1][y].equals(thisColor)) {
				ret += countNeighbors(x - 1, y, prevx, prevy);
			}
		}
		if (x + 1 < colors.length) {
			if (colors[x +  1][y].equals(thisColor)) {
				ret += countNeighbors(x +  1, y, prevx, prevy);
			}
		}
		if (y >= 1) {
			if (colors[x][y - 1].equals(thisColor)) {
				ret += countNeighbors(x, y - 1, prevx, prevy);
			}
		}
		if (y + 1 < colors[0].length) {
			if (colors[x][y - 1].equals(thisColor)) {
				ret += countNeighbors(x, y + 1, prevx, prevy);
			}
		}
		return ret;
	}
	public int countNeighbors(int x, int y, int[] fx, int[] fy) {
		TileColor thisColor = colors[x][y];
		int[] prevx = new int[fx.length + 1];
		for (int i = 0; i < fx.length;++i) {
			prevx[i] = fx[i];
		}
		prevx[fx.length] = x;
		int[] prevy = new int[fy.length + 1];
		for (int i = 0; i < fy.length;++i) {
			prevy[i] = fy[i];
		}
		prevy[fy.length] = y;
		
		int ret = 0;
		if (x >= 1 && !EnigUtils.containsPoint(prevx, prevy, y, x - 1)) {
			if (colors[x - 1][y].equals(thisColor)) {
				ret += countNeighbors(x - 1, y, prevx, prevy);
			}
		}
		if (x + 1 < colors.length && !EnigUtils.containsPoint(prevx, prevy, y, x + 1)) {
			if (colors[x +  1][y].equals(thisColor)) {
				ret += countNeighbors(x +  1, y, prevx, prevy);
			}
		}
		if (y >= 1 && !EnigUtils.containsPoint(prevx, prevy, y - 1, x)) {
			if (colors[x][y - 1].equals(thisColor)) {
				ret += countNeighbors(x, y - 1, prevx, prevy);
			}
		}
		if (y + 1 < colors[0].length && !EnigUtils.containsPoint(prevx, prevy, y + 1, x)) {
			if (colors[x][y - 1].equals(thisColor)) {
				ret += countNeighbors(x, y + 1, prevx, prevy);
			}
		}
		return ret;
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
				red.realColor = redColor;
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
}
