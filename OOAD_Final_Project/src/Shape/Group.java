package Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 1. 畫 Group 裡的shape
 2. 畫 Group 矩形框
 3. 設定 Group 邊界
 4. 重新定位因為 Drag 而位移 Group 的矩形框以及 Group 裡的所有 Shape
 5. Group 中的 shape 改名
 **/
public class Group extends MyGraphic {
    private List<MyGraphic> myGraphics = new ArrayList<>();
    private Rectangle bounds = new Rectangle();
    private MyGraphic selectedMyGraphic = null;

    // 畫 Obj 物件
    public void draw(Graphics g) {
        for (MyGraphic myGraphic : myGraphics) {
            myGraphic.draw(g);
        }
    }

    // 畫 Group 的矩形框
    public void paint(Graphics g) {
        int offset = 10;
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x - offset, bounds.y - offset, bounds.width + offset * 2, bounds.height + offset * 2);
        if (selectedMyGraphic != null) {
            selectedMyGraphic.paint(g);
        }
    }

    // 位移重新定位
    public void resetLocation(int moveX, int moveY) {
        // 將 Group 裡的物件重新定位
        for (MyGraphic myGraphic : myGraphics) {
            myGraphic.resetLocation(moveX, moveY);
        }
        resetBounds(moveX, moveY);
    }

    // 判斷是不是點在 shape 裡
    public String sideString(Point p) {
        for (MyGraphic myGraphic : myGraphics) {
            String judgeInside = myGraphic.sideString(p);
            if (judgeInside != null) {
                // 判斷選取哪個 shape
                selectedMyGraphic = myGraphic;
                return "insideGroup";
            }
        }
        return null;
    }

    public void changeName(String name) {
        if (selectedMyGraphic != null) {
            selectedMyGraphic.changeName(name);
        }
    }

    public void resetSelectedShape() {
        selectedMyGraphic = null;
    }

    public MyGraphic getSelectedShape() {
        return selectedMyGraphic;
    }

    // 設置邊界
    public void setBounds() {
        Point upLeftP;
        Point bottomRightP;
        int leftX = myGraphics.stream().mapToInt(MyGraphic::getX1).min().orElse(Integer.MAX_VALUE);
        int rightX = myGraphics.stream().mapToInt(MyGraphic::getX2).max().orElse(Integer.MIN_VALUE);
        int upY = myGraphics.stream().mapToInt(MyGraphic::getY1).min().orElse(Integer.MAX_VALUE);
        int bottomY = myGraphics.stream().mapToInt(MyGraphic::getY2).max().orElse(Integer.MIN_VALUE);

        upLeftP = new Point(leftX, upY);
        bottomRightP = new Point(rightX, bottomY);
        bounds.setBounds(upLeftP.x, upLeftP.y, Math.abs(upLeftP.x - bottomRightP.x), Math.abs(upLeftP.y - bottomRightP.y));

        x1 = bounds.x;
        y1 = bounds.y;
        x2 = bounds.x + bounds.width;
        y2 = bounds.y + bounds.height;
    }

    // 更新位移後的座標
    protected void resetBounds(int moveX, int moveY) {
        bounds.setLocation(bounds.x + moveX, bounds.y + moveY);
        x1 = bounds.x;
        y1 = bounds.y;
        x2 = bounds.x + bounds.width;
        y2 = bounds.y + bounds.height;
    }

    public void addShapes(MyGraphic myGraphic) {
        myGraphics.add(myGraphic);
    }

    public List<MyGraphic> getShapes() {
        return myGraphics;
    }
}
