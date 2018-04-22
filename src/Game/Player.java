package Game;

import engine.EnigWindow;

public class Player {
	public float xpos = 250f;
	public float ypos = 2200f;
	public float yvel = 0f;
	public int swapCD = 0;
	public TileCol.TileColor selection;
	public Player() {
		selection = TileCol.TileColor.red;
	}
	public void testActions(TileCol closest) {
	
	}
	public void updateMovement(TileCol closest) {
		TileCol.TileColor belowLeftj = closest.checkPoint((xpos % 800) + 1f, ypos + yvel - TileCol.BOX_SIZE-5f);
		TileCol.TileColor belowRightj = closest.checkPoint((xpos % 800) + TileCol.BOX_SIZE - 1f, ypos + yvel - TileCol.BOX_SIZE-5f);
		boolean llj = true;
		if (belowLeftj == null) {
			llj = false;
		}else if (belowLeftj.equals(selection)) {
			llj = false;
		}
		boolean lrj = true;
		if (belowRightj == null) {
			lrj = false;
		}else if (belowRightj.equals(selection)) {
			lrj = false;
		}
		
		if (EnigWindow.mainWindow.keys[UserControls.up] > 0) {
			if (llj || lrj) {
				yvel = 20f;
			}
		}
		
		TileCol.TileColor aboveLeft = closest.checkPoint((xpos % 800) + 5f, ypos + yvel);
		TileCol.TileColor aboveRight = closest.checkPoint((xpos % 800) + TileCol.BOX_SIZE - 5f, ypos + yvel);
		boolean al = true;
		if (aboveLeft == null) {
			al = false;
		}else if (aboveLeft.equals(selection)) {
			al = false;
		}
		boolean ar = true;
		if (aboveRight == null) {
			ar = false;
		}else if (aboveRight.equals(selection)) {
			ar = false;
		}
		TileCol.TileColor belowLeft = closest.checkPoint((xpos % 800) + 5f, ypos + yvel - TileCol.BOX_SIZE);
		TileCol.TileColor belowRight = closest.checkPoint((xpos % 800) + TileCol.BOX_SIZE - 5f, ypos + yvel - TileCol.BOX_SIZE);
		boolean ll = true;
		if (belowLeft == null) {
			ll = false;
		}else if (belowLeft.equals(selection)) {
			ll = false;
		}
		boolean lr = true;
		if (belowRight == null) {
			lr = false;
		}else if (belowRight.equals(selection)) {
			lr = false;
		}
		if (yvel > 0) {
			
			if (ar || al) {
				yvel = 0f;
				ypos = Math.round(ypos/TileCol.BOX_SIZE)*TileCol.BOX_SIZE;
			}else {
				ypos += yvel;
				yvel -= 1f;
			}
		}else {
			if (lr || ll) {
				yvel = 0f;
				ypos = Math.round(ypos/TileCol.BOX_SIZE)*TileCol.BOX_SIZE;
			}else {
				ypos += yvel;
				yvel -= 1f;
			}
		}
		
		TileCol.TileColor rightUp = closest.checkPoint((xpos % 800) + TileCol.BOX_SIZE + 10.1f, ypos - 5f);
		TileCol.TileColor rightDown = closest.checkPoint((xpos % 800) + TileCol.BOX_SIZE + 10.1f, ypos - TileCol.BOX_SIZE + 5f);
		boolean ru = true;
		if (rightUp == null) {
			ru = false;
		}else if (rightUp.equals(selection)) {
			ru = false;
		}
		boolean rd = true;
		if (rightDown == null) {
			rd = false;
		}else if (rightDown.equals(selection)) {
			rd = false;
		}
		if (EnigWindow.mainWindow.keys[UserControls.right] > 0) {
			if (!(rd || ru)) {
				if (xpos + 960.1f > ProjectMain.wallpos) {
				
				}else {
					xpos += 10.1f;
				}
			}else {
				xpos = Math.round(xpos/TileCol.BOX_SIZE) * TileCol.BOX_SIZE;
			}
		}
		TileCol.TileColor leftUp = closest.checkPoint((xpos % 800) - 10.1f, ypos - 5f);
		TileCol.TileColor leftDown = closest.checkPoint((xpos % 800) - 10.1f, ypos - TileCol.BOX_SIZE + 5f);
		boolean lu = true;
		if (leftUp == null) {
			lu = false;
		}else if (leftUp.equals(selection)) {
			lu = false;
		}
		boolean ld = true;
		if (leftDown == null) {
			ld = false;
		}else if (leftDown.equals(selection)) {
			ld = false;
		}
		if (EnigWindow.mainWindow.keys[UserControls.left] > 0) {
			if (!(ld || lu)) {
				xpos -= 10.1f;
			}else {
				xpos = Math.round(xpos/TileCol.BOX_SIZE) * TileCol.BOX_SIZE;
			}
		}
		
		if (EnigWindow.mainWindow.keys[UserControls.sdown] > 0) {
			TileCol.TileColor below = closest.checkPoint((xpos % 800) + TileCol.BOX_SIZE/2f, ypos - 1.5f*TileCol.BOX_SIZE);
			if (below != null) {
				xpos = Math.round(xpos/100) * 100;
				ypos -= 5f;
				selection = below;
			}
		}
		if (EnigWindow.mainWindow.keys[UserControls.sleft] > 0 && swapCD <= 0) {
			float x1 = (xpos % 800) + TileCol.BOX_SIZE/2f;
			float y1 = ypos - 0.5f*TileCol.BOX_SIZE;
			float x2 = (xpos % 800) - TileCol.BOX_SIZE/2f;
			float y2 = ypos - 0.5f*TileCol.BOX_SIZE;
			closest.safeSwapPoints(x1, y1, x2, y2);
			int[][] matching = closest.countNeighbors(-1 + (int) (x1/TileCol.BOX_SIZE), (int) (y1/TileCol.BOX_SIZE));
			if (matching[0].length > 3 && !closest.colors[matching[0][0]][matching[1][0]].equals(selection)) {
				ProjectMain.score += matching[0].length * ProjectMain.mult;
				closest.fillPoints(matching, selection);
			}
			matching = closest.countNeighbors(-1 + (int) (x2/TileCol.BOX_SIZE), (int) (y2/TileCol.BOX_SIZE));
			if (matching[0].length > 3 && !closest.colors[matching[0][0]][matching[1][0]].equals(selection)) {
				ProjectMain.score += matching[0].length * ProjectMain.mult;
				closest.fillPoints(matching, selection);
			}
			swapCD = 11;
		}
		if (EnigWindow.mainWindow.keys[UserControls.sright] > 0 && swapCD <= 0) {
			float x1 = (xpos % 800) + TileCol.BOX_SIZE/2f;
			float y1 = ypos - 0.5f*TileCol.BOX_SIZE;
			float x2 = (xpos % 800) + 1.5f*TileCol.BOX_SIZE;
			float y2 = ypos - 0.5f*TileCol.BOX_SIZE;
			closest.safeSwapPoints(x1, y1, x2, y2);
			int[][] matching = closest.countNeighbors(-1 + (int) (x1/TileCol.BOX_SIZE), (int) (y1/TileCol.BOX_SIZE));
			if (matching[0].length > 3 && !closest.colors[matching[0][0]][matching[1][0]].equals(selection)) {
				ProjectMain.score += matching[0].length * ProjectMain.mult;
				closest.fillPoints(matching, selection);
			}
			matching = closest.countNeighbors(-1 + (int) (x2/TileCol.BOX_SIZE), (int) (y2/TileCol.BOX_SIZE));
			if (matching[0].length > 3 && !closest.colors[matching[0][0]][matching[1][0]].equals(selection)) {
				ProjectMain.score += matching[0].length * ProjectMain.mult;
				closest.fillPoints(matching, selection);
			}
			swapCD = 11;
		}
		if (EnigWindow.mainWindow.keys[UserControls.sup] > 0 && swapCD <= 0) {
			float x1 = (xpos % 800) + TileCol.BOX_SIZE/2f;
			float y1 = ypos - 0.5f*TileCol.BOX_SIZE;
			float x2 = (xpos % 800) + TileCol.BOX_SIZE/2f;
			float y2 = ypos + 0.5f*TileCol.BOX_SIZE;
			closest.safeSwapPoints(x1, y1, x2, y2);
			int[][] matching = closest.countNeighbors(-1 + (int) (x1/TileCol.BOX_SIZE), (int) (y1/TileCol.BOX_SIZE));
			if (matching[0].length > 3 && !closest.colors[matching[0][0]][matching[1][0]].equals(selection)) {
				ProjectMain.score += matching[0].length * ProjectMain.mult;
				closest.fillPoints(matching, selection);
			}
			matching = closest.countNeighbors(-1 + (int) (x2/TileCol.BOX_SIZE), (int) (y2/TileCol.BOX_SIZE));
			if (matching[0].length > 3 && !closest.colors[matching[0][0]][matching[1][0]].equals(selection)) {
				ProjectMain.score += matching[0].length * ProjectMain.mult;
				closest.fillPoints(matching, selection);
			}
			swapCD = 11;
		}
		if (yvel < -TileCol.BOX_SIZE/2f) {
			yvel = -TileCol.BOX_SIZE/2f;
		}else if (yvel > TileCol.BOX_SIZE/2f) {
			yvel = TileCol.BOX_SIZE/2f;
		}
		
		--swapCD;
	}
}
