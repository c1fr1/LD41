package Game;

import engine.EnigWindow;

public class Player {
	public float xpos = 10f;
	public float ypos = 2000f;
	public float yvel = 0f;
	public TileCol.TileColor selection;
	public Player() {
		selection = TileCol.TileColor.red;
	}
	public void updateMovement(TileCol closest) {
		TileCol.TileColor belowLeftj = closest.checkPoint(xpos, ypos + yvel - TileCol.BOX_SIZE-5f);
		TileCol.TileColor belowRightj = closest.checkPoint(xpos + TileCol.BOX_SIZE, ypos + yvel - TileCol.BOX_SIZE-5f);
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
		
		TileCol.TileColor aboveLeft = closest.checkPoint(xpos + 5f, ypos + yvel);
		TileCol.TileColor aboveRight = closest.checkPoint(xpos + TileCol.BOX_SIZE - 5f, ypos + yvel);
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
		TileCol.TileColor belowLeft = closest.checkPoint(xpos + 5f, ypos + yvel - TileCol.BOX_SIZE);
		TileCol.TileColor belowRight = closest.checkPoint(xpos + TileCol.BOX_SIZE - 5f, ypos + yvel - TileCol.BOX_SIZE);
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
			}else {
				ypos += yvel;
				yvel -= 1f;
			}
		}else {
			if (lr || ll) {
				yvel = 0f;
			}else {
				ypos += yvel;
				yvel -= 1f;
			}
		}
		
		TileCol.TileColor rightUp = closest.checkPoint(xpos + TileCol.BOX_SIZE + 5.1f, ypos - 5f);
		TileCol.TileColor rightDown = closest.checkPoint(xpos + TileCol.BOX_SIZE + 5.1f, ypos - TileCol.BOX_SIZE + 5f);
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
				xpos += 5.1f;
			}
		}
		TileCol.TileColor leftUp = closest.checkPoint(xpos - 5.1f, ypos - 5f);
		TileCol.TileColor leftDown = closest.checkPoint(xpos - 5.1f, ypos - TileCol.BOX_SIZE + 5f);
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
				xpos -= 5.1f;
			}
		}
	}
}
