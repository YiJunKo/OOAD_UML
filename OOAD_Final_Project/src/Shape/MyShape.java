package Shape;

import java.awt.*;

/**
 在 MyShape 就先處理好的事情
 1. 畫 shape 的 port
 2. 判斷 mousePress 是否在物件裡
 3. mouseDrag 後重新定位
 4. changeName
 6. 創物件時創 port
 **/

public abstract class MyShape extends MyGraphic {
    private int offset = 5;
    protected int width;
    protected int height;
    protected String objectName = "Object Name";
    protected Font font = new Font(Font.DIALOG, Font.BOLD, 14);
    protected Port[] ports = new Port[4];

    // 預設每個shape畫法都不同需各自實作
    public abstract void draw(Graphics g);

    public void changeName(String name){
        this.objectName = name;
    }

    // 創建物件時創 Port
    protected void setPorts() {
        // 上下左右
        int[] xpoint = {(x1+x2)/2, x2 + offset, (x1+x2)/2, x1 - offset};
        int[] ypoint = {y1 - offset, (y1+y2)/2, y2+offset, (y1+y2)/2};

        for(int i = 0; i < ports.length; i++) {
            Port port = new Port();
            port.setPort(xpoint[i], ypoint[i], offset);
            ports[i] = port;
        }
    }

    // 畫 ports
    public void paint(Graphics g) {
        for(int i = 0; i < ports.length; i++) {
            g.fillRect(ports[i].x, ports[i].y, ports[i].width, ports[i].height);
        }
    }

    // 判斷是否點在物件裡
    public String sideString(Point p) {
        Point center = new Point();
        center.x = (x1 + x2) / 2;       // 中心點X
        center.y = (y1 + y2) / 2;       // 中心點Y
        Point[] points = { new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2) };  // 四點座標

        for (int i = 0; i < points.length; i++) {
            Polygon t = new Polygon();
            int secondIndex = ((i + 1) % 4);
            t.addPoint(points[i].x, points[i].y);
            t.addPoint(points[secondIndex].x, points[secondIndex].y);
            t.addPoint(center.x, center.y);

            // 在物件中
            if (t.contains(p)) {
                return Integer.toString(i);
            }
        }
        // 不在物件中
        return null;
    }

    public Port getPort(int portIndex) {
        return ports[portIndex];
    }

    // 重新定位
    public void resetLocation(int moveX, int moveY) {
        int x1 = this.x1 + moveX;
        int y1 = this.y1 + moveY;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + width;
        this.y2 = y1 + height;

        // 中上、中右、中下、中左
        int[] xpoint = {(x1+x2)/2, x2 + offset, (x1+x2)/2, x1 - offset};
        int[] ypoint = {y1 - offset, (y1+y2)/2, y2+offset, (y1+y2)/2};

        for(int i = 0; i < ports.length; i++) {
            ports[i].setPort(xpoint[i], ypoint[i], offset);
            ports[i].resetLines();
        }
    }
}