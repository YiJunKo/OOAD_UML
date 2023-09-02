package Shape;

import java.awt.*;

/**
 1.  abstract Graphic
 **/
public abstract class MyGraphic {
    protected int x1, y1, x2, y2;
    public boolean group_selected = false;

    public abstract void draw(Graphics g);

    // 判斷是否在選取區的座標
    public int getX1(){
        return x1;
    }
    public int getY1(){
        return y1;
    }
    public int getX2(){
        return x2;
    }
    public int getY2(){
        return y2;
    }

    public void resetLocation(){}   // for Line
    public void resetLocation(int moveX, int moveY){}  // for Basic object and Group

    public void changeName(String name){}
    // 畫 port 或 矩形框
    public void paint(Graphics g){}
    // 判斷是否點在裡面
    public String sideString(Point p){
        return null;
    }
    // Basic object
    public Port getPort(int portIndex){
        return null;
    }

    // reset 選取狀態
    public void resetSelectedShape() {}
    public MyGraphic getSelectedShape() {
        return null;
    }
}
