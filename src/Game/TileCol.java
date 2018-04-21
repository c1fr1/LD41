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
		posx = xOffset;
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
	public void fillPoints(int[][] pos, TileColor color) {
		for (int i = 0; i < pos[0].length;++i) {
			colors[pos[0][i]][pos[1][i]] = color;
		}
	}
	public boolean safeSwapPoints(float x1, float y1, float x2, float y2) {
		int x1ind = -1 + (int) (x1/BOX_SIZE);
		if (x1ind < 0) {
			return false;
		}
		if (x1ind >= colors.length) {
			return false;
		}
		int y1ind = (int) (y1/BOX_SIZE);
		if (y1ind < 0) {
			return false;
		}
		if (y1ind >= colors[0].length) {
			return false;
		}
		int x2ind = -1 + (int) (x2/BOX_SIZE);
		if (x2ind < 0) {
			return false;
		}
		if (x2ind >= colors.length) {
			return false;
		}
		int y2ind = (int) (y2/BOX_SIZE);
		if (y2ind < 0) {
			return false;
		}
		if (y2ind >= colors[0].length) {
			return false;
		}
		TileColor temp = colors[x1ind][y1ind];
		colors[x1ind][y1ind] = colors[x2ind][y2ind];
		colors[x2ind][y2ind] = temp;
		return false;
	}
	public void swapPoints(int x1, int y1, int x2, int y2) {
		TileColor temp = colors[x1][y1];
		colors[x1][y1] = colors[x2][y2];
		colors[x2][y2] = temp;
	}
	public void setPoint(float x, float y, TileColor nc) {
		int xind = -1 + (int) (x/BOX_SIZE);
		if (xind < 0) {
			return;
		}
		if (xind >= colors.length) {
			return;
		}
		int yind = (int) (y/BOX_SIZE);
		if (yind < 0) {
			return;
		}
		if (yind >= colors[0].length) {
			return;
		}
		colors[xind][yind] = nc;
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
	public int[][] countNeighbors(int x, int y) {
		if (x < 0) {
			return new int[][] {new int[]{}, new int[]{}};
		}
		if (x >= colors.length) {
			return new int[][] {new int[]{}, new int[]{}};
		}
		if (y < 0) {
			return new int[][] {new int[]{}, new int[]{}};
		}
		if (y >= colors[0].length) {
			return new int[][] {new int[]{}, new int[]{}};
		}
		TileColor thisColor = colors[x][y];
		int[] prevx = new int[] {x};
		int[] prevy = new int[] {y};
		int ret = 0;
		if (x >= 1 && !EnigUtils.containsPoint(prevx, prevy, x - 1, y)) {
			if (colors[x - 1][y].equals(thisColor)) {
				int[][] t = countNeighbors(x - 1, y, prevx, prevy);
				prevx = EnigUtils.addArrays(prevx, t[0]);
				prevy = EnigUtils.addArrays(prevy, t[1]);
			}
		}
		if (x + 1 < colors.length && !EnigUtils.containsPoint(prevx, prevy, x + 1, y)) {
			if (colors[x + 1][y].equals(thisColor)) {
				int[][] t = countNeighbors(x + 1, y, prevx, prevy);
				prevx = EnigUtils.addArrays(prevx, t[0]);
				prevy = EnigUtils.addArrays(prevy, t[1]);
			}
		}
		if (y >= 1 && !EnigUtils.containsPoint(prevx, prevy, x, y - 1)) {
			if (colors[x][y - 1].equals(thisColor)) {
				int[][] t = countNeighbors(x, y - 1, prevx, prevy);
				prevx = EnigUtils.addArrays(prevx, t[0]);
				prevy = EnigUtils.addArrays(prevy, t[1]);
			}
		}
		if (y + 1 < colors[0].length && !EnigUtils.containsPoint(prevx, prevy, x, y + 1)) {
			if (colors[x][y + 1].equals(thisColor)) {
				int[][] t = countNeighbors(x, y + 1, prevx, prevy);
				prevx = EnigUtils.addArrays(prevx, t[0]);
				prevy = EnigUtils.addArrays(prevy, t[1]);
			}
		}
		return new int[][] {prevx, prevy};
	}
	public int[][] countNeighbors(int x, int y, int[] fx, int[] fy) {
		TileColor thisColor = colors[x][y];
		
		int[] newx = new int[] {x};
		int[] newy = new int[] {y};
		if (x >= 1 && (!EnigUtils.containsPoint(fx, fy, x - 1, y) && !EnigUtils.containsPoint(newx, newy, x - 1, y))) {
			if (colors[x - 1][y].equals(thisColor)) {
				int[][] t = countNeighbors(x - 1, y, EnigUtils.addArrays(newx, fx), EnigUtils.addArrays(newy, fy));//
				newx = EnigUtils.addArrays(newx, t[0]);
				newy = EnigUtils.addArrays(newy, t[1]);
			}
		}
		if (x + 1 < colors.length && (!EnigUtils.containsPoint(fx, fy, x + 1, y) && !EnigUtils.containsPoint(newx, newy, x + 1, y))) {
			if (colors[x + 1][y].equals(thisColor)) {
				int[][] t = countNeighbors(x +  1, y, EnigUtils.addArrays(newx, fx), EnigUtils.addArrays(newy, fy));//
				newx = EnigUtils.addArrays(newx, t[0]);
				newy = EnigUtils.addArrays(newy, t[1]);
			}
		}
		if (y >= 1 && (!EnigUtils.containsPoint(fx, fy, x, y - 1) && !EnigUtils.containsPoint(newx, newy, x, y - 1))) {
			if (colors[x][y - 1].equals(thisColor)) {
				int[][] t = countNeighbors(x, y - 1, EnigUtils.addArrays(newx, fx), EnigUtils.addArrays(newy, fy));
				newx = EnigUtils.addArrays(newx, t[0]);
				newy = EnigUtils.addArrays(newy, t[1]);
			}
		}
		if (y + 1 < colors[0].length && (!EnigUtils.containsPoint(fx, fy, x, y + 1) && !EnigUtils.containsPoint(newx, newy, x, y + 1))) {
			if (colors[x][y + 1].equals(thisColor)) {
				int[][] t = countNeighbors(x, y + 1, EnigUtils.addArrays(newx, fx), EnigUtils.addArrays(newy, fy));
				newx = EnigUtils.addArrays(newx, t[0]);
				newy = EnigUtils.addArrays(newy, t[1]);
			}
		}
		return new int[][] {newx, newy};
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
}
