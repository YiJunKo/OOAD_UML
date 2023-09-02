package MainFrame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JPanel;
import Mode.Mode;
import Shape.Group;
import Shape.MyGraphic;

/**
 1. Canvas 做 Singleton 確保只有一個 Canvas
 2. 畫布上的所有 shape 都會存在這個 List 中
**/
public class Canvas extends JPanel {
	private static Canvas instance = null; // 實作 Singleton
	private EventListener listener = null;
	protected Mode currentMode = null;

	private List<MyGraphic> myGraphics = new ArrayList<MyGraphic>(); // List 存 ShapeObj

	public MyGraphic tempLine = null;
	public Rectangle SelectedArea = new Rectangle();
	public MyGraphic selectedObj = null;
	private Canvas() {	}

	// 實作 Singleton 確保單一物件
	public static Canvas getInstance() {
		if (instance == null) {
			synchronized (Canvas.class) {
				if (instance == null) {
					instance = new Canvas();
				}
			}
		}
		return instance;
	}

	// 畫出 Canvas
	public void paint(Graphics g) {
		// Canvas 基本設定
		Dimension dim = getSize();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, dim.width, dim.height);

		// 畫上去的顏色
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));

		// 從 List 中畫上每一個 Shape
		for (int i = myGraphics.size() - 1; i >= 0; i--) {
			MyGraphic myGraphic = myGraphics.get(i);
			myGraphic.draw(g);
			myGraphic.group_selected = false;
			/* check group select */
			if (!SelectedArea.isEmpty() && checkSelectedArea(myGraphic)) {
				myGraphic.paint(g);
				myGraphic.group_selected = true;
			}
		}

		// 畫出 drag 中暫時的線
		if (tempLine != null) {
			tempLine.draw(g);
		}

		// 畫出 select port
		if (this.selectedObj != null) {
			selectedObj.paint(g);
		}

		// 畫出 select 時的矩形框
		if (!SelectedArea.isEmpty()) {
			g.setColor(Color.BLACK);
			g.drawRect(SelectedArea.x, SelectedArea.y, SelectedArea.width, SelectedArea.height);
		}
	}

	// set toolBar 的 Mode
	public void setCurrentMode() {
		// 先移除目前的滑鼠事件 避免多個監聽器
		removeMouseListener((MouseListener) listener);
		removeMouseMotionListener((MouseMotionListener) listener);

		// 抓取現在新的監聽器並加入
		listener = currentMode;
		addMouseListener((MouseListener) listener);
		addMouseMotionListener((MouseMotionListener) listener);
	}
	
	public void reset() {
		if(selectedObj != null){
			selectedObj.resetSelectedShape();   // for selected shape inside the group
			selectedObj = null;
		}
		SelectedArea.setBounds(0, 0, 0, 0);
	}

	// Shape 加入 List
	public void addShape(MyGraphic myGraphic) {

		myGraphics.add(myGraphic);
	}

	// 抓取 List 中有哪些 Shape
	public List<MyGraphic> getShapeList() {

		return this.myGraphics;
	}

	public void groupShape() {
		// 建立 group 物件
		Group group = new Group();

		// 找出如果被選取到的 shape 加進去 group 中 並從 shapes 中刪除
		for (int i = 0; i < myGraphics.size(); i++) {
			MyGraphic myGraphic = myGraphics.get(i);
			if (myGraphic.group_selected) {
				group.addShapes(myGraphic);
				myGraphics.remove(i);
				i--;
			}
		}
		group.setBounds();

		// 最後將 group 視為一個物件加入 shape 中
		myGraphics.add(group);
	}

	// 解除 group
	public void removeGroup() {
		Group group = (Group) selectedObj;
		List<MyGraphic> groupMyGraphics = group.getShapes();
		for(int i = 0; i < groupMyGraphics.size(); i++){
			MyGraphic myGraphic = groupMyGraphics.get(i);
			myGraphics.add(myGraphic);
		}
		myGraphics.remove(selectedObj);
	}

	// 改名字
	public void reName(String name) {
		if(selectedObj != null){
			selectedObj.changeName(name);
			repaint();
		}
	}

	// 判斷是否有在 select area 中
	private boolean checkSelectedArea(MyGraphic myGraphic) {
		Point upperleft = new Point(myGraphic.getX1(), myGraphic.getY1());
		Point lowerright = new Point(myGraphic.getX2(), myGraphic.getY2());
		/* show ports of selected objects */
		if (SelectedArea.contains(upperleft) && SelectedArea.contains(lowerright)) {
			return true;
		}
		return false;
	}
}
