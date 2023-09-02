package Shape;

import java.awt.*;

/** 實作畫 class shape **/
public class classObj extends MyShape {
    public classObj(int x1, int y1) {
        this.width = 100;
        this.height = 120;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + width;
        this.y2 = y1 + height;
        setPorts();
    }
    public void draw(Graphics g) {
        // 畫 class 外型
        g.drawRect(x1, y1, width, height);
        int part = height / 3;
        g.drawLine(x1, y1 + part, x2, y1 + part);
        g.drawLine(x1, y1 + part * 2, x2, y1 + part * 2);

        // 找到位置並畫上 class 名字
        int stringWidth = g.getFontMetrics(font).stringWidth(objectName);
        double empty = (Math.abs(x1-x2) - stringWidth)/2;
        g.setFont(font);
        g.drawString(objectName, x1 + (int)empty, y1 + 25);
    }
}
